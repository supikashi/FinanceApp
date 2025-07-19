package com.spksh.domain.repository

import com.spksh.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для получения данных об категориях
 */
interface CategoryRepository {
    fun getCategoriesFlow(): Flow<List<Category>>
    fun getCategoriesByTypeFlow(isIncome: Boolean): Flow<List<Category>>
    suspend fun loadFromNetwork(): List<Category>?
//    suspend fun getCategories(): List<Category>
//    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>
}