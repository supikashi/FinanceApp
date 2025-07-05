package com.spksh.financeapp.ui.state

import com.spksh.financeapp.domain.model.Transaction
import com.spksh.financeapp.ui.utils.formatSum
import com.spksh.financeapp.ui.model.CategoryWithSumUIModel

/**
 * Модель для экранов доходов и расходов
 */
data class TransactionScreenState(
    val sum: String,
    val categories: List<CategoryWithSumUIModel>
) {
    companion object {
        fun getByTransactions(currency: String, transactions: List<Transaction>) : TransactionScreenState {
            val categories = transactions
                .groupBy { Pair(it.category, it.comment) }
                .toList()
                .map { CategoryWithSumUIModel(
                    id = it.first.first.id,
                    name = it.first.first.name,
                    emoji = it.first.first.emoji,
                    isIncome = it.first.first.isIncome,
                    description = if (it.first.second == "") null else it.first.second,
                    sum = formatSum(it.second.sumOf { it.amount }, currency)
                )
                }
            val sum = formatSum(transactions.sumOf { it.amount }, currency)
            return TransactionScreenState(sum, categories)
        }
    }
}

