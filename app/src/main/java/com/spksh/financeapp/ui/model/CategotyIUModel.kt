package com.spksh.financeapp.ui.model

import com.spksh.financeapp.domain.model.Category

/**
 * UI-модель категории
 */
data class CategoryUIModel(
    val id: Long = 0,
    val name: String = "",
    val emoji: String? = null,
    val isIncome: Boolean = false
)

fun Category.toUiModel() = CategoryUIModel(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)