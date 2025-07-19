package com.spksh.domain.useCase

import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Use-case для получения списка категорий из [CategoryRepository]
 */
class GetCategoriesFlowUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> = repository.getCategoriesFlow()
}