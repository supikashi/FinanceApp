package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Category
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Use-case для фильтрации категорий по имени.
 * Принимает на вход список категорий и строку для фильтрации
 */
class FilterCategoriesByNameUseCase @Inject constructor() {
    suspend operator fun invoke(categories: List<Category>, name: String): List<Category> = withContext(Dispatchers.Default) {
        categories.filter {
            it.name.lowercase().contains(name.lowercase())
        }
    }
}