package com.spksh.transactions.domain.use_case

import com.spksh.domain.model.AnalysisData
import com.spksh.domain.model.CategoryAnalysis
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoryAnalysisFlowUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        accountId: Long,
        startDate: Long,
        endDate: Long,
        isIncome: Boolean
    ): Flow<AnalysisData> = repository.getTransactionsByPeriodFlow(
        accountId, startDate, endDate
    ).map { transactions ->
        val sum = transactions.filter { it.category.isIncome == isIncome}.sumOf { it.amount }
        val categories = transactions
            .filter { it.category.isIncome == isIncome}
            .groupBy { it.category }
            .toList()
            .map {
                val categorySum = it.second.sumOf { it.amount }
                CategoryAnalysis(
                    id = it.first.id,
                    name = it.first.name,
                    emoji = it.first.emoji,
                    isIncome = it.first.isIncome,
                    sum = categorySum,
                    percentage = (categorySum / sum) * 100
                )
            }
        AnalysisData(
            categories = categories,
            sum = sum,
        )
    }
}