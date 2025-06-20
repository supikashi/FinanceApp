package com.spksh.financeapp.data.remote.api


import com.spksh.financeapp.data.remote.model.CategoryDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDTO>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<CategoryDTO>
}