package com.spksh.domain.repository

import com.spksh.domain.model.Category

/**
 * Интерфейс репозитория для получения данных об категориях
 */
interface CategoryRepository {
    suspend fun getCategories(): List<Category>

    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>
}