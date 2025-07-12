package com.spksh.transactions.domain.use_case

import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionRequest
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionRequest: TransactionRequest): Transaction = withContext(Dispatchers.IO) {
        repository.createTransaction(transactionRequest)
    }
}