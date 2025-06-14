package com.spksh.financeapp.ui.models

data class CategoryWithSumUIModel(
    val id: Long = 0,
    val name: String = "",
    val emoji: String? = null,
    val sum: String = "",
    val isIncome: Boolean = false,
    val description: String? = null,
)
