package com.spksh.financeapp.domain.model

/**
 * Доменная модель категории
 */
data class Category(
    val id: Long = 0,
    val name: String = "",
    val emoji: String = "",
    val isIncome: Boolean = false
)