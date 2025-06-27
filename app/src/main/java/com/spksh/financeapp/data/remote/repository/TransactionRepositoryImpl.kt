package com.spksh.financeapp.data.remote.repository

import com.spksh.financeapp.data.remote.api.TransactionApiService
import com.spksh.financeapp.domain.model.Transaction
import com.spksh.financeapp.domain.repository.TransactionRepository
import jakarta.inject.Inject

/**
 * Реализация [TransactionRepository], получающая данные о транзакциях из API
 */
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApiService
) : TransactionRepository {
    override suspend fun getTransactionsByPeriod(
        accountId: Long,
        startDate: String?,
        endDate: String?
    ): List<Transaction> {
        return api.getCategories(accountId, startDate, endDate).map { it.toTransaction() }
    }
}