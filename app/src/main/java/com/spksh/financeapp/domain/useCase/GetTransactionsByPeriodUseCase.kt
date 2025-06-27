package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Transaction
import com.spksh.financeapp.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    ): List<Transaction> = withContext(Dispatchers.IO) {
        repository.getTransactionsByPeriod(
            accountId = accountId,
            startDate = startDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
            endDate = endDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        )
    }
}