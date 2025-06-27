package com.spksh.financeapp.data.remote.api

import com.spksh.financeapp.data.remote.model.AccountDTO
import retrofit2.http.GET

/**
 * Retrofit-сервис для получения счетов с сервера.
 */
interface AccountApiService {
    @GET("accounts")
    suspend fun getAccounts(): List<AccountDTO>
}