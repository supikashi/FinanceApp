package com.spksh.settings.domain.use_case

import com.spksh.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveSyncTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(time: Long) {
        dataStoreRepository.saveSyncTime(time)
    }
}