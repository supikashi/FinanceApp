package com.spksh.transactions.domain.use_case

import com.spksh.domain.model.TransactionResponse
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Use-case для получения списка транзакций за период из [TransactionRepository
 */
class GetTransactionsByPeriodUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Long,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): List<TransactionResponse> = withContext(Dispatchers.IO) {
        repository.getTransactionsByPeriod(
            accountId = accountId,
            startDate = startDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
            endDate = endDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        )
    }
}