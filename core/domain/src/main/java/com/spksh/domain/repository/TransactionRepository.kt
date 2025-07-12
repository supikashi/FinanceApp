package com.spksh.domain.repository

import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionRequest
import com.spksh.domain.model.TransactionResponse

/**
 * Интерфейс репозитория для получения данных о транзакциях
 */
interface TransactionRepository {
    suspend fun getTransaction(
        id: Long
    ) : TransactionResponse

    suspend fun getTransactionsByPeriod(
        accountId: Long,
        startDate: String?,
        endDate: String?
    ) : List<TransactionResponse>

    suspend fun createTransaction(
        transactionRequest: TransactionRequest
    ) : Transaction

    suspend fun updateTransaction(
        id: Long,
        transactionRequest: TransactionRequest
    ) : TransactionResponse

    suspend fun deleteTransaction(
        id: Long
    )
}