package com.spksh.financeapp.domain.repository

import com.spksh.financeapp.domain.model.Account

interface AccountRepository {
    suspend fun getAccounts(): List<Account>
}