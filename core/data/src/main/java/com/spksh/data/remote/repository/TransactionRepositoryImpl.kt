package com.spksh.data.remote.repository

import com.spksh.domain.repository.TransactionRepository
import com.spksh.data.remote.api.TransactionApiService
import com.spksh.data.remote.model.toDTO
import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionRequest
import com.spksh.domain.model.TransactionResponse
import jakarta.inject.Inject

/**
 * Реализация [com.spksh.domain.repository.TransactionRepository], получающая данные о транзакциях из API
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService
) : TransactionRepository {
    override suspend fun getTransaction(id: Long): TransactionResponse {
        return api.getTransaction(id).toTransactionResponse()
    }

    override suspend fun getTransactionsByPeriod(
        accountId: Long,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponse> {
        return api.getTransactions(accountId, startDate, endDate).map { it.toTransactionResponse() }
    }

    override suspend fun createTransaction(transactionRequest: TransactionRequest): Transaction {
        return api.createTransaction(transactionRequest.toDTO()).toTransaction()
    }

    override suspend fun updateTransaction(
        id: Long,
        transactionRequest: TransactionRequest
    ): TransactionResponse {
        return api.updateTransaction(id, transactionRequest.toDTO()).toTransactionResponse()
    }

    override suspend fun deleteTransaction(id: Long) {
        api.deleteTransaction(id)
    }
}