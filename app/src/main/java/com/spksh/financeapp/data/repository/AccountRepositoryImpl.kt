package com.spksh.financeapp.data.repository

import com.spksh.financeapp.data.remote.api.AccountApiService
import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay


class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApiService
) : AccountRepository {
    override suspend fun getAccounts(): List<Account> {
        delay(100)
        return api.getAccounts().map { it.toAccount() }
    }
}