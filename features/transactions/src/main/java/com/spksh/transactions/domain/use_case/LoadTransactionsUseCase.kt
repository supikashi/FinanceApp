package com.spksh.transactions.domain.use_case

import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(accountId: Long) = withContext(Dispatchers.IO) {
        repository.loadFromNetwork(accountId)
    }
}