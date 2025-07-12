package com.spksh.data.remote.api

import com.spksh.data.remote.model.TransactionDTO
import com.spksh.data.remote.model.TransactionResponseDTO
import com.spksh.data.remote.model.TransactionRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit-сервис для получения транзакций с сервера.
 */
interface TransactionApiService {
    @GET("transactions/{id}")
    suspend fun getTransaction(
        @Path("id") id: Long
    ) : TransactionResponseDTO

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Path("accountId") accountId: Long,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ) : List<TransactionResponseDTO>

    @POST("transactions")
    suspend fun createTransaction(
        @Body transactionRequestDTO: TransactionRequestDTO
    ) : TransactionDTO

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Long,
        @Body transactionRequestDTO: TransactionRequestDTO
    ) : TransactionResponseDTO

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Path("id") id: Long,
    )
}