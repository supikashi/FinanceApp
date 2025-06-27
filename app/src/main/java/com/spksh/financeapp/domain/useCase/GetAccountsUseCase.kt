package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case для получения списка аккаунтов из [AccountRepository]
 */
class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): List<Account> = withContext(Dispatchers.IO)  {
        repository.getAccounts()
    }
}