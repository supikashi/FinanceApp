package com.spksh.transactions.domain.use_case

import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Long) = withContext(Dispatchers.IO) {
        repository.deleteTransaction(id)
    }
}