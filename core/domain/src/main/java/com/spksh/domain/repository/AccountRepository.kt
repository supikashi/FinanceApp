package com.spksh.domain.repository

import com.spksh.domain.model.Account
import com.spksh.domain.model.AccountUpdateData
import kotlinx.coroutines.flow.StateFlow

/**
 * Интерфейс репозитория для получения данных об аккаунтах
 */
interface AccountRepository {
    fun getAccountsFlow(): StateFlow<List<Account>>
    suspend fun updateAccount(accountId: Long, updateData: AccountUpdateData): Account
    suspend fun loadAccounts()
}