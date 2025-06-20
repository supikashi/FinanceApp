package com.spksh.financeapp.ui.state

import com.spksh.financeapp.domain.model.Transaction
import com.spksh.financeapp.ui.features.formatCurrency
import com.spksh.financeapp.ui.features.formatDouble
import com.spksh.financeapp.ui.model.CategoryWithSumUIModel

sealed class TransactionUiState {
    object Loading : TransactionUiState()

    data class Error(val message: String) : TransactionUiState()

    data class Success(
        val sum: String,
        val categories: List<CategoryWithSumUIModel>
    ) : TransactionUiState()

    companion object {
        fun getByTransactions(currency: String ,transactions: List<Transaction>) : Success {
            val categories = transactions
                .groupBy { Pair(it.category, it.comment) }
                .toList()
                .map { CategoryWithSumUIModel(
                    id = it.first.first.id,
                    name = it.first.first.name,
                    emoji = it.first.first.emoji,
                    isIncome = it.first.first.isIncome,
                    description = if (it.first.second == "") null else it.first.second,
                    sum = "${formatDouble(it.second.sumOf { it.amount })} ${formatCurrency(currency)}"
                )
                }
            val sum = "${formatDouble(transactions.sumOf { it.amount })} ${formatCurrency(currency)}"
            return Success(sum, categories)
        }
    }
}