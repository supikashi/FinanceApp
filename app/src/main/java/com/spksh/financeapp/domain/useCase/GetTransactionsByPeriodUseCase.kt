package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Transaction
import com.spksh.financeapp.domain.repository.TransactionRepository
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetTransactionsByPeriodUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Long,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): List<Transaction> {
        return repository.getTransactionsByPeriod(
            accountId = accountId,
            startDate = startDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
            endDate = endDate?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        )
    }
}