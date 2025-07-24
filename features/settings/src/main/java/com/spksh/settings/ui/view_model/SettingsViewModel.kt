package com.spksh.settings.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.repository.DataStoreRepository
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.SynchronizeDatabaseUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val synchronizeDatabaseUseCase: SynchronizeDatabaseUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val getZoneIdUseCase: GetZoneIdUseCase,
) : ViewModel() {
    private val zoneId = getZoneIdUseCase()
    val state = dataStoreRepository.getSyncTimeFlow().map {
        it?.let {
            Instant.ofEpochMilli(it).atZone(zoneId).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),null)
    fun sync() {
        viewModelScope.launch {
            try {
                synchronizeDatabaseUseCase()
                Log.i("my_tag", "sync success")
            } catch (e: Exception) {
                Log.i("my_tag", "sync error ${e.message}")
            }
        }
    }
}