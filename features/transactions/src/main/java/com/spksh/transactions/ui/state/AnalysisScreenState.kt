package com.spksh.transactions.ui.state

import com.spksh.transactions.ui.model.CategoryAnalysisUiModel
import java.time.LocalDate

data class AnalysisScreenState(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startDateString: String,
    val endDateString: String,
    val sum: String = "",
    val categories: List<CategoryAnalysisUiModel> = emptyList(),
)