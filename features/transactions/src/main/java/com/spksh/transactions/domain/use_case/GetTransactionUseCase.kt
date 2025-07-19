package com.spksh.transactions.domain.use_case

import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionResponse
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Long): Transaction? = withContext(Dispatchers.IO) {
        repository.getTransaction(id)
    }
}