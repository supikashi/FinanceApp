package com.spksh.transactions.domain.use_case

import com.spksh.domain.model.TransactionResponse
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use-case для получения списка транзакций за период из [TransactionRepository
 */
class GetTransactionsByPeriodFlowUseCase @Inject constructor(
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