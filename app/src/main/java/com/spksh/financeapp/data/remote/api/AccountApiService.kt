package com.spksh.financeapp.data.remote.api

import com.spksh.financeapp.data.remote.model.AccountDTO
import retrofit2.http.GET

interface AccountApiService {
    @GET("accounts")
    suspend fun getAccounts(): List<AccountDTO>
}