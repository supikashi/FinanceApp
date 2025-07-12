package com.spksh.category.ui.state

import com.spksh.ui.model.CategoryUIModel

/**
 * Модель для экрана категорий
 */
data class CategoryScreenState(
    val categories: List<CategoryUIModel>,
    val filteredCategories: List<CategoryUIModel>
)