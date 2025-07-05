package com.spksh.financeapp.domain.useCase

import com.spksh.financeapp.domain.model.AccountUpdateData
import com.spksh.financeapp.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use-case для изменения данных счета
 */
class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(
        accountId: Long,
        updateData: AccountUpdateData
    ) = withContext(Dispatchers.IO)  {
        repository.updateAccount(accountId, updateData)
    }
}