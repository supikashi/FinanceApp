package com.spksh.financeapp.data.repository

import com.spksh.financeapp.data.remote.api.CategoryApiService
import com.spksh.financeapp.domain.model.Category
import com.spksh.financeapp.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApiService
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map {it.toCategory()}
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        delay(100)
        return api.getCategoriesByType(isIncome).map { it.toCategory() }
    }
}