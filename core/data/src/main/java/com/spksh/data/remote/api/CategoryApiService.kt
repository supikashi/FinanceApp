package com.spksh.data.remote.api


import com.spksh.data.remote.model.CategoryDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit-сервис для получения категорий с сервера.
 */
interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDTO>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<CategoryDTO>
}