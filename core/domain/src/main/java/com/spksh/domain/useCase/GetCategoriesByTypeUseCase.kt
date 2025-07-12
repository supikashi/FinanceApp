package com.spksh.domain.useCase

import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case для получения списка категорий по типу из [CategoryRepository]
 */
class GetCategoriesByTypeUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(isIncome: Boolean): List<Category> = withContext(Dispatchers.IO) {
        repository.getCategoriesByType(isIncome)
    }
}