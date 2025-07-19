package com.spksh.domain.useCase

import android.util.Log
import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.DataStoreRepository
import com.spksh.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class SynchronizeDatabaseUseCase(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val res = categoryRepository.loadFromNetwork()
        if (res != null) {
            accountRepository.synchronizeUpdated()
            accountRepository.synchronizeDatabase()
            transactionRepository.synchronizeUpdated()
            transactionRepository.synchronizeDatabase()
            Log.i("my_tag", "work success")
            dataStoreRepository.saveSyncTime(Instant.now().toEpochMilli())
        }
    }
}