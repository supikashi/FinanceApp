package com.spksh.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getSyncTimeFlow() : Flow<Long?>
    suspend fun saveSyncTime(time: Long)
    fun getAppLanguage(): Flow<String>
    suspend fun saveAppLanguage(language: String)
    suspend fun saveThemeMode(isDarkTheme: Boolean)
    fun getThemeMode(): Flow<Boolean>
    suspend fun saveMainColor(color: Int)
    fun getMainColor(): Flow<Int>
}