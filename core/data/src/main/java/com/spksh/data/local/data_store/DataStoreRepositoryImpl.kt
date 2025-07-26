package com.spksh.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.spksh.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {
    companion object {
        private val SYNC_TIME = stringPreferencesKey("sync_time")
        private val APP_LANGUAGE = stringPreferencesKey("app_language")
        private val THEME_MODE = booleanPreferencesKey("theme_mode")
        private val MAIN_COLOR = intPreferencesKey("main_color")
        const val SYSTEM_LANGUAGE = "system"
        const val ENGLISH = "en"
        const val RUSSIAN = "ru"
    }
    private val Context.dataStore by preferencesDataStore(name = "settings")

    override fun getSyncTimeFlow(): Flow<Long?> = context.dataStore.data
        .map { preferences ->
            preferences[SYNC_TIME]?.toLong()
        }

    override suspend fun saveSyncTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[SYNC_TIME] = time.toString()
        }
    }

    override fun getAppLanguage(): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[APP_LANGUAGE] ?: SYSTEM_LANGUAGE
        }

    override suspend fun saveAppLanguage(language: String) {
        if (language !in setOf(SYSTEM_LANGUAGE, ENGLISH, RUSSIAN)) {
            throw IllegalArgumentException("Invalid language value: $language")
        }

        context.dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = language
        }
    }
    override suspend fun saveThemeMode(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = isDarkTheme
        }
    }

    override fun getThemeMode(): Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[THEME_MODE] ?: false }

    override suspend fun saveMainColor(color: Int) {
        context.dataStore.edit { preferences ->
            preferences[MAIN_COLOR] = color
        }
    }

    override fun getMainColor(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[MAIN_COLOR] ?: 0 }
}