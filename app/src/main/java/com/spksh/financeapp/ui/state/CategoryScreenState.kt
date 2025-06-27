package com.spksh.financeapp.ui.state

import com.spksh.financeapp.ui.model.CategoryUIModel

/**
 * Модель для экрана категорий
 */
data class CategoryScreenState(
    val categories: List<CategoryUIModel>
)