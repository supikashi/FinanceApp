package com.spksh.transactions.domain.use_case

import com.spksh.domain.model.TransactionRequest
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        id: Long,
        transactionRequest: TransactionRequest
    ) = withContext(Dispatchers.IO) {
        repository.updateTransaction(id, transactionRequest)
    }
}