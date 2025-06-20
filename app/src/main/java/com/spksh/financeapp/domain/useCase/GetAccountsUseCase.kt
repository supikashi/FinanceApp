package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject


class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): List<Account> {
        return repository.getAccounts()
    }
}