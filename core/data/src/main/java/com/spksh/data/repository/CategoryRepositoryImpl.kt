package com.spksh.data.repository

import android.util.Log
import com.spksh.data.local.dao.CategoryDao
import com.spksh.domain.repository.CategoryRepository
import com.spksh.data.remote.api.CategoryApiService
import com.spksh.data.remote.model.CategoryDTO
import com.spksh.domain.model.Category
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

/**
 * Реализация [CategoryRepository], получающая данные о категориях из API
 */
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApiService,
    private val dao: CategoryDao
) : CategoryRepository {
    override fun getCategoriesFlow(): Flow<List<Category>> {
        return dao.getAllItemsFlow().map {it.map {it.toCategory()}}
    }

    override fun getCategoriesByTypeFlow(isIncome: Boolean): Flow<List<Category>> {
        return dao.getAllItemsByTypeFlow(isIncome).map {it.map {it.toCategory()}}
    }

    override suspend fun loadFromNetwork(): List<Category>? {
        try {
            val local = dao.getAllItems().map {it.toCategory()}
            if (local.isEmpty()) {
                val response = api.getCategories()
                dao.insertAll(response.map {it.toEntity()})
                Log.i("my_tag", "category load success")
                return response.map {it.toCategory()}
            } else {
                Log.i("my_tag", "local category load success")
                return local
            }
        } catch (e: Exception) {
            Log.i("my_tag", "category load error")
            return null
        }
    }

//    override suspend fun getCategories(): List<Category> {
//        return api.getCategories().map {it.toCategory()}
//    }
//
//    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
//        return api.getCategoriesByType(isIncome).map { it.toCategory() }
//    }
}