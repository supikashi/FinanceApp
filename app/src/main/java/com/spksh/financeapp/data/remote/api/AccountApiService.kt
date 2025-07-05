package com.spksh.financeapp.data.remote.api

import com.google.android.gms.common.api.Response
import com.spksh.financeapp.data.remote.model.AccountDTO
import com.spksh.financeapp.data.remote.model.AccountUpdateDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit-сервис для получения счетов с сервера.
 */
interface AccountApiService {
    @GET("accounts")
    suspend fun getAccounts(): List<AccountDTO>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Long,
        @Body updateRequest: AccountUpdateDTO
    ): AccountDTO
}