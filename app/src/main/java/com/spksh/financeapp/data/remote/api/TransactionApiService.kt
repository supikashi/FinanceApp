package com.spksh.financeapp.data.remote.api

import com.spksh.financeapp.data.remote.model.TransactionDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApiService {
    @GET("transactions/account/{accountId}/period")
    suspend fun getCategories(
        @Path("accountId") accountId: Long,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): List<TransactionDTO>
}