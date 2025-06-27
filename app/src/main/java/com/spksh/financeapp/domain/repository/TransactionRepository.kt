package com.spksh.financeapp.domain.repository

import com.spksh.financeapp.domain.model.Transaction

/**
 * Интерфейс репозитория для получения данных о транзакциях
 */
interface TransactionRepository {
    suspend fun getTransactionsByPeriod(
        accountId: Long,
        startDate: String?,
        endDate: String?
    ): List<Transaction>
}