package com.spksh.domain.model

data class AnalysisData(
    val categories: List<CategoryAnalysis> = emptyList(),
    val sum: Double = 0.0,
    val currency: String = ""
)