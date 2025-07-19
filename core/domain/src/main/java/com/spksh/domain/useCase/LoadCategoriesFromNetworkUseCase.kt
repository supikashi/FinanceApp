package com.spksh.domain.useCase

import com.spksh.domain.model.Category
import com.spksh.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadCategoriesFromNetworkUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category>? = withContext(Dispatchers.IO) {
        repository.loadFromNetwork()
    }
}