package com.spksh.domain.model

data class CategoryAnalysis(
    val id: Long = 0,
    val name: String = "",
    val emoji: String = "",
    val isIncome: Boolean = false,
    val comment: String? = null,
    val sum: Double = 0.0,
    val percentage: Double = 0.0
)