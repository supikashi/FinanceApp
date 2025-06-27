package com.spksh.financeapp.ui.state

import com.spksh.financeapp.ui.model.TransactionUiModel
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
    val transactions: List<TransactionUiModel>
)
