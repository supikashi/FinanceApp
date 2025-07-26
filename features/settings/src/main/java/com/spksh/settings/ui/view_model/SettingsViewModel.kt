package com.spksh.settings.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.SynchronizeDatabaseUseCase
import com.spksh.settings.domain.use_case.GetAppLanguageUseCase
import com.spksh.settings.domain.use_case.GetMainColorUseCase
import com.spksh.settings.domain.use_case.GetSyncTimeUseCase
import com.spksh.settings.domain.use_case.GetThemeUseCase
import com.spksh.settings.domain.use_case.SaveAppLanguageUseCase
import com.spksh.settings.domain.use_case.SaveMainColorUseCase
import com.spksh.settings.domain.use_case.SaveThemeUseCase
import com.spksh.settings.ui.state.SettingsScreenState
import com.spksh.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val synchronizeDatabaseUseCase: SynchronizeDatabaseUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
    private val saveAppLanguageUseCase: SaveAppLanguageUseCase,
    private val getAppLanguageUseCase: GetAppLanguageUseCase,
    private val getSyncTimeUseCase: GetSyncTimeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    private val getMainColorUseCase: GetMainColorUseCase,
    private val saveMainColorUseCase: SaveMainColorUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<SettingsScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<SettingsScreenState>> = _uiState
    private val zoneId = getZoneIdUseCase()
    init {
        viewModelScope.launch {
            combine(
                getAppLanguageUseCase(),
                getSyncTimeUseCase(),
                getThemeUseCase(),
                getMainColorUseCase()
            ) { language, syncTime, isDarkTheme, color ->
                SettingsScreenState(
                    currentLanguage = language,
                    syncTime = syncTime?.let {
                        Instant.ofEpochMilli(it)
                            .atZone(zoneId)
                            .toLocalDateTime()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    },
                    isDarkTheme = isDarkTheme,
                    color = color
                )
            }.collect {
                _uiState.value = UiState.Success(data = it)
            }
        }
    }
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

    suspend fun saveLanguage(language: String) {
        saveAppLanguageUseCase(language)
    }

    suspend fun saveTheme(isDarkTheme: Boolean) {
        saveThemeUseCase(isDarkTheme)
    }

    suspend fun saveColor(color: Int) {
        saveMainColorUseCase(color)
    }
}