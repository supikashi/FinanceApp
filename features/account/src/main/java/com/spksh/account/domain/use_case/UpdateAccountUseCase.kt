package com.spksh.account.domain.use_case

import com.spksh.domain.model.AccountUpdateData
import com.spksh.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

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