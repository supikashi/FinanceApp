package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Category
import com.spksh.financeapp.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case для получения списка категорий по типу из [CategoryRepository]
 */
class GetCategoriesByTypeUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(isIncome: Boolean): List<Category> = withContext(Dispatchers.IO) {
        repository.getCategoriesByType(isIncome)
    }
}