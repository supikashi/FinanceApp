package com.spksh.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.spksh.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val syncTime = stringPreferencesKey("sync_time")

    override fun getSyncTimeFlow(): Flow<Long?> = context.dataStore.data
        .map { preferences ->
            preferences[syncTime]?.toLong()
        }

    override suspend fun saveSyncTime(time: Long) {
        Log.i("my_tag", "save datastore $time")
        context.dataStore.edit { preferences ->
            Log.i("my_tag", "in save datastore $time")
            preferences[syncTime] = time.toString()
        }
    }
}