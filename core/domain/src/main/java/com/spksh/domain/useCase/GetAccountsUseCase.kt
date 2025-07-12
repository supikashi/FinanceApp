package com.spksh.domain.useCase

import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.model.Account
import kotlinx.coroutines.flow.StateFlow

/**
 * Use-case для получения списка аккаунтов из [AccountRepository]
 */
class GetAccountsFlowUseCase(
    private val repository: AccountRepository
) {
    operator fun invoke(): StateFlow<List<Account>> = repository.getAccountsFlow()
}