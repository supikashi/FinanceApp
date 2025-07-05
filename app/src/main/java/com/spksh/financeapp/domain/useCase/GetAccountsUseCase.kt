package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

/**
 * Use-case для получения списка аккаунтов из [AccountRepository]
 */
class GetAccountsFlowUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): StateFlow<List<Account>> = repository.getAccountsFlow()
}