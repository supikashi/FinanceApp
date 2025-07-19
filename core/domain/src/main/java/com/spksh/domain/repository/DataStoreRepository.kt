package com.spksh.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getSyncTimeFlow() : Flow<Long?>
    suspend fun saveSyncTime(time: Long)
}