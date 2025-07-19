package com.spksh.account.domain.use_case

import com.spksh.domain.model.Account
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
        account: Account
    ) = withContext(Dispatchers.IO)  {
        repository.updateAccount(account)
    }
}