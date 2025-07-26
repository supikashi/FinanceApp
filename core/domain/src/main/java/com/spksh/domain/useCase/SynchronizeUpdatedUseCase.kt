package com.spksh.domain.useCase

import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SynchronizeUpdatedUseCase(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val res = categoryRepository.loadFromNetwork()
        if (res != null) {
            accountRepository.synchronizeUpdated()
            transactionRepository.synchronizeUpdated()
        }
    }
}