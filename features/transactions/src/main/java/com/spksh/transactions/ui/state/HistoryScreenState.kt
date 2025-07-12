package com.spksh.transactions.ui.state

import com.spksh.transactions.ui.model.TransactionResponseUiModel
import java.time.LocalDate

/**
 * Модель для экранов историй
 */
data class HistoryScreenState(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startDateString: String,
    val endDateString: String,
    val sum: String,
    val transactions: List<TransactionResponseUiModel>
)