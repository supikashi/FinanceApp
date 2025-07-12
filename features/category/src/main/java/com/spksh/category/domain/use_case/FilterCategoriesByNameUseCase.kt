package com.spksh.category.domain.use_case

import com.spksh.domain.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use-case для фильтрации категорий по имени.
 * Принимает на вход список категорий и строку для фильтрации
 */
class FilterCategoriesByNameUseCase @Inject constructor() {
    suspend operator fun invoke(categories: List<Category>, name: String): List<Category> = withContext(
        Dispatchers.Default) {
        categories.filter {
            it.name.lowercase().contains(name.lowercase())
        }
    }
}