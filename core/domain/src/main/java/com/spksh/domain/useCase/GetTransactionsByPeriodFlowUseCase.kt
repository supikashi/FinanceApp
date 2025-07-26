package com.spksh.domain.useCase

import com.spksh.domain.model.TransactionResponse
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use-case для получения списка транзакций за период из [TransactionRepository
 */
class GetTransactionsByPeriodFlowUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        accountId: Long,
        startDate: Long,
        endDate: Long
    ): Flow<List<TransactionResponse>> =
        repository.getTransactionsByPeriodFlow(
            accountId = accountId,
            startDate = startDate,
            endDate = endDate,
        )
}