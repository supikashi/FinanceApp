package com.spksh.domain.useCase

import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Use-case для получения списка категорий по типу из [CategoryRepository]
 */
class GetCategoriesByTypeFlowUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(isIncome: Boolean): Flow<List<Category>> =
        repository.getCategoriesByTypeFlow(isIncome)
}