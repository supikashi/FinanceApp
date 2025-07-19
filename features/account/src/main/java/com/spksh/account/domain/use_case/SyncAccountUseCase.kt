package com.spksh.account.domain.use_case

import com.spksh.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO)  {
        repository.synchronizeUpdated()
        repository.synchronizeDatabase()
    }
}