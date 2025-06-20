package com.spksh.financeapp.domain.repository

import com.spksh.financeapp.domain.model.Transaction

interface TransactionRepository {
    suspend fun getTransactionsByPeriod(
        accountId: Long,
        startDate: String?,
        endDate: String?
    ): List<Transaction>
}