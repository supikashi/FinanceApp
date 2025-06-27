package com.spksh.financeapp.data.remote.repository

import com.spksh.financeapp.data.remote.api.AccountApiService
import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject

/**
 * Реализация [AccountRepository], получающая данные об аккаунтах из API
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApiService
) : AccountRepository {
    override suspend fun getAccounts(): List<Account> {
        return api.getAccounts().map { it.toAccount() }
    }
}