package com.spksh.settings.domain.use_case

import com.spksh.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppLanguageUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<String> = dataStoreRepository.getAppLanguage()
}