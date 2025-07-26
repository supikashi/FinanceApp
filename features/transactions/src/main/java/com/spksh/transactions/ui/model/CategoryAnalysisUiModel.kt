package com.spksh.transactions.ui.model

import androidx.compose.ui.graphics.Color
import com.spksh.domain.model.CategoryAnalysis
import com.spksh.graph.model.TransactionPlotModel
import com.spksh.ui.utils.formatSum
import kotlin.Long

data class CategoryAnalysisUiModel(
    val id: Long = 0,
    val name: String = "",
    val emoji: String = "",
    val isIncome: Boolean = false,
    val comment: String? = null,
    val sum: Double = 0.0,
    val sumString: String = "",
    val percentage: Double = 0.0,
    val percentageString: String = ""
) {
    fun toPlotModel() = TransactionPlotModel(
        id = id,
        proportion = (percentage / 100).toFloat(),
        text = "$percentageString $name"
    )
}

fun CategoryAnalysis.toUiModel(currency: String) = CategoryAnalysisUiModel(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome,
    comment = comment,
    sum = sum,
    sumString = formatSum(sum, currency),
    percentage = percentage,
    percentageString = formatSum(percentage, "%")
)