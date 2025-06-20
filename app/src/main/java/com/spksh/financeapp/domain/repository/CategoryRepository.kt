package com.spksh.financeapp.domain.repository

import com.spksh.financeapp.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>

    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>
}