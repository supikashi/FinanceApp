package com.spksh.financeapp.ui.state

import com.spksh.financeapp.ui.model.TransactionUiModel
import java.time.LocalDate

sealed class HistoryUiState {
    object Loading : HistoryUiState()

    data class Error(val message: String) : HistoryUiState()

    data class Success(
        val startDate: LocalDate,
        val endDate: LocalDate,
        val startDateString: String,
        val endDateString: String,
        val sum: String,
        val transactions: List<TransactionUiModel>
    ) : HistoryUiState()
}