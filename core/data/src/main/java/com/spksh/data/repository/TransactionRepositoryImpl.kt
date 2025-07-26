package com.spksh.data.repository

import com.spksh.data.local.dao.AccountDao
import com.spksh.data.local.dao.TransactionDao
import com.spksh.data.local.model.TransactionEntity
import com.spksh.data.remote.api.TransactionApiService
import com.spksh.data.remote.model.toDTO
import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionRequest
import com.spksh.domain.model.TransactionResponse
import com.spksh.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant

/**
 * Реализация [TransactionRepository], получающая данные о транзакциях из API
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) : TransactionRepository {
    override suspend fun getTransaction(id: Long): Transaction? {
        return transactionDao.getItemById(id)?.toTransaction()
    }

    override fun getTransactionsByPeriodFlow(
        accountId: Long,
        startDate: Long,
        endDate: Long
    ): Flow<List<TransactionResponse>> {
        return transactionDao.getTransactionsByPeriodFlow(accountId, startDate, endDate)
            .map {
                it.filter { !it.transactionEntity.isDeleted }
                    .map {it.toTransactionResponse()}
            }
    }

    override suspend fun createTransaction(transactionRequest: TransactionRequest) : Long {
        val account = accountDao.getAllItems().find {it.localId == transactionRequest.accountId}
        account?.remoteId?.let { accountId ->
            val now = Instant.now().toString()
            val id = transactionDao.insert(
                TransactionEntity(
                    accountId = transactionRequest.accountId,
                    categoryId = transactionRequest.categoryId,
                    amount = transactionRequest.amount,
                    transactionDate = transactionRequest.transactionDate.toEpochMilli(),
                    comment = transactionRequest.comment,
                    createdAt = now,
                    updatedAt = now,
                    isSync = false
                )
            )
            try {
                val transaction = api.createTransaction(transactionRequest.copy(accountId = accountId).toDTO()).toTransaction()
                transactionDao.update(
                    TransactionEntity(
                        localId = id,
                        remoteId = transaction.remoteId,
                        accountId = transactionRequest.accountId,
                        categoryId = transaction.categoryId,
                        amount = transaction.amount,
                        transactionDate = transaction.transactionDate.toEpochMilli(),
                        comment = transaction.comment,
                        createdAt = transaction.createdAt,
                        updatedAt = transaction.updatedAt,
                        isSync = true
                    )
                )
            } catch (e: Exception) {

            }
            return id
        }
        throw Exception()
    }

    override suspend fun updateTransaction(
        id: Long,
        transactionRequest: TransactionRequest
    ) {
        val oldTransaction = transactionDao.getItemById(id)
        oldTransaction?.let {
            val account = accountDao.getAllItems().find {it.localId == transactionRequest.accountId}
            account?.remoteId?.let { accountId ->
                transactionDao.update(
                    it.copy(
                        accountId = transactionRequest.accountId,
                        categoryId = transactionRequest.categoryId,
                        amount = transactionRequest.amount,
                        transactionDate = transactionRequest.transactionDate.toEpochMilli(),
                        comment = transactionRequest.comment,
                        updatedAt = Instant.now().toString(),
                        isSync = false
                    )
                )
                if (it.remoteId != null) {
                    try {
                        val remoteTransaction = api.updateTransaction(
                            id = it.remoteId,
                            transactionRequestDTO = transactionRequest.copy(accountId = accountId).toDTO()
                        )
                        transactionDao.update(
                            remoteTransaction.toEntity().copy(
                                localId = it.localId,
                                accountId = transactionRequest.accountId
                            )
                        )
                    } catch (e: Exception) {

                    }
                } else {
                    try {
                        val remoteTransaction = api.createTransaction(
                            transactionRequestDTO = transactionRequest.copy(accountId = accountId).toDTO()
                        )
                        transactionDao.update(
                            remoteTransaction.toEntity().copy(
                                localId = it.localId,
                                accountId = transactionRequest.accountId
                            )
                        )
                    } catch (e: Exception) {

                    }
                }
            }
        }
    }

    override suspend fun deleteTransaction(id: Long) {
        val transaction = transactionDao.getItemById(id)
        transaction?.let {
            if (it.remoteId == null) {
                transactionDao.delete(it)
            } else {
                transactionDao.update(
                    it.copy(
                        isDeleted = true,
                        isSync = false,
                        updatedAt = Instant.now().toString(),
                    )
                )
                try {
                    api.deleteTransaction(it.remoteId)
                    transactionDao.delete(it)
                } catch (e: Exception) {

                }
            }
        }
    }

    override suspend fun synchronizeDatabase() {
        accountDao.getAllItems().forEach { account ->
            account.remoteId?.let { remoteAccountId ->
                val localData = transactionDao.getAllItemsByAccount(account.localId)
                val remoteData = api.getTransactions(
                    remoteAccountId,
                    "0000-01-01",
                    "9999-12-31"
                )
                remoteData.forEach { remoteTransaction ->
                    val localTransaction = localData.find { it.remoteId == remoteTransaction.id }
                    if (localTransaction == null) {
                        transactionDao.insert(remoteTransaction.toEntity().copy(accountId = account.localId))
                    } else {
                        transactionDao.update(remoteTransaction.toEntity()
                            .copy(
                                localTransaction.localId,
                                accountId = account.localId
                            )
                        )
                    }
                }
                localData.forEach { localTransaction ->
                    val remoteTransaction = remoteData.find { it.id == localTransaction.remoteId }
                    if (remoteTransaction == null) {
                        transactionDao.delete(localTransaction)
                    }
                }
            }
        }
    }

    override suspend fun synchronizeUpdated() {
        accountDao.getAllItems().forEach { account ->
            account.remoteId?.let { accountId ->
                val unsynced = transactionDao.getUnsyncedItems()
                val remoteTransactions = api.getTransactions(
                    accountId,
                    "0000-01-01",
                    "9999-12-31"
                )
                unsynced.forEach { localTransaction ->
                    if (localTransaction.remoteId == null) {
                        val remoteTransaction = api.createTransaction(
                            localTransaction.toRequestDTO().copy(
                                accountId = accountId
                            )
                        )
                        transactionDao.update(
                            remoteTransaction.toEntity(localTransaction.localId)
                                .copy(
                                    accountId = account.localId
                                )
                        )
                    } else {
                        val remoteTransaction = remoteTransactions.find { it.id == localTransaction.remoteId }
                        if (remoteTransaction == null) {
                            transactionDao.delete(localTransaction)
                        } else {
                            val remoteTime = Instant.parse(remoteTransaction.updatedAt)
                            val localTime = Instant.parse(localTransaction.updatedAt)
                            if (remoteTime.isBefore(localTime)) {
                                if (localTransaction.isDeleted) {
                                    api.deleteTransaction(remoteTransaction.id)
                                    transactionDao.delete(localTransaction)
                                } else {
                                    val response = api.updateTransaction(
                                        id = remoteTransaction.id,
                                        transactionRequestDTO = localTransaction.toRequestDTO().copy(
                                            accountId = accountId
                                        )
                                    )
                                    transactionDao.update(
                                        response.toEntity(localTransaction.localId).copy(
                                            accountId = localTransaction.accountId
                                        )
                                    )
                                }
                            } else {
                                transactionDao.update(
                                    remoteTransaction.toEntity(localTransaction.localId).copy(
                                        accountId = localTransaction.accountId
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}