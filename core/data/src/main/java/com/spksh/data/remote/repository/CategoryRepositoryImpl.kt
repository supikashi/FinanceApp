package com.spksh.data.remote.repository

import com.spksh.domain.repository.CategoryRepository
import com.spksh.data.remote.api.CategoryApiService
import com.spksh.domain.model.Category
import jakarta.inject.Inject

/**
 * Реализация [com.spksh.domain.repository.CategoryRepository], получающая данные о категориях из API
 */
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApiService
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map {it.toCategory()}
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        return api.getCategoriesByType(isIncome).map { it.toCategory() }
    }
}