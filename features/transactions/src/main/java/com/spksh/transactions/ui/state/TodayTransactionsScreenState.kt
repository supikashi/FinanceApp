package com.spksh.transactions.ui.state

import com.spksh.domain.model.TransactionResponse
import com.spksh.transactions.ui.model.TransactionResponseUiModel
import com.spksh.transactions.ui.model.toUiModel
import com.spksh.ui.utils.formatSum

/**
 * Модель для экранов доходов и расходов
 */
data class TodayTransactionsScreenState(
    val sum: String,
    val transactions: List<TransactionResponseUiModel>
) {
    companion object {
        fun getByTransactions(currency: String, transactionResponses: List<TransactionResponse>) : TodayTransactionsScreenState {
            val sum = formatSum(transactionResponses.sumOf { it.amount }, currency)
            return TodayTransactionsScreenState(sum, transactionResponses.map {it.toUiModel()})
        }
    }
}