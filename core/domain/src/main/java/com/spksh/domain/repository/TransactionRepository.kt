package com.spksh.domain.repository

import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionRequest
import com.spksh.domain.model.TransactionResponse
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для получения данных о транзакциях
 */
interface TransactionRepository {
    suspend fun getTransaction(
        id: Long
    ) : Transaction?

    fun getTransactionsByPeriodFlow(
        accountId: Long,
        startDate: Long,
        endDate: Long
    ) : Flow<List<TransactionResponse>>

    suspend fun createTransaction(
        transactionRequest: TransactionRequest
    ) : Long

    suspend fun updateTransaction(
        id: Long,
        transactionRequest: TransactionRequest
    )

    suspend fun deleteTransaction(
        id: Long
    )
    suspend fun synchronizeDatabase()
    suspend fun synchronizeUpdated()
}