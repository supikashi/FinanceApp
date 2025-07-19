package com.spksh.domain.useCase

import com.spksh.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case для обновления списка аккаунтов
 */
class LoadAccountsUseCase(
    private val repository: AccountRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO)  {
        repository.loadFromNetwork()
    }
}