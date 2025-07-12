package com.spksh.transactions.ui.model

/**
 * UI-модель категории с суммой транзакций
 */
data class CategoryWithSumUIModel(
    val id: Long = 0,
    val name: String = "",
    val emoji: String? = null,
    val isIncome: Boolean = false,
    val description: String? = null,
    val sum: String = "",
)