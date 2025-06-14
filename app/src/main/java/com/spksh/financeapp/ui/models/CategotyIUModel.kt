package com.spksh.financeapp.ui.models

data class CategoryUIModel(
    val id: Long = 0,
    val name: String = "",
    val emoji: String? = null,
    val isIncome: Boolean = false,
    val description: String? = null,
)