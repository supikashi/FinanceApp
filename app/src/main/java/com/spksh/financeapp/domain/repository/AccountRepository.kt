package com.spksh.financeapp.domain.repository

import com.spksh.financeapp.domain.model.Account

/**
 * Интерфейс репозитория для получения данных об аккаунтах
 */
interface AccountRepository {
    suspend fun getAccounts(): List<Account>
}