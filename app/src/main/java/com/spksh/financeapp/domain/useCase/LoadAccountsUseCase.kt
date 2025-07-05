package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case для обновления списка аккаунтов
 */
class LoadAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO)  {
        repository.loadAccounts()
    }
}