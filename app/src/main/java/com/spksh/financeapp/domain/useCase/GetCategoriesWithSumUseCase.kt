package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Category
import com.spksh.financeapp.domain.repository.CategoryRepository
import jakarta.inject.Inject

class GetCategoriesWithSumUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
}