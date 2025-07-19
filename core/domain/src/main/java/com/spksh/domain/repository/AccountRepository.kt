package com.spksh.domain.repository

import com.spksh.domain.model.Account
import com.spksh.domain.model.Category
import kotlinx.coroutines.flow.StateFlow

/**
 * Интерфейс репозитория для получения данных об аккаунтах
 */
interface AccountRepository {
    fun getAccountsFlow(): StateFlow<List<Account>>
    suspend fun updateAccount(account: Account)
    suspend fun synchronizeDatabase()
    suspend fun synchronizeUpdated()
    suspend fun loadFromNetwork(): List<Account>?
}