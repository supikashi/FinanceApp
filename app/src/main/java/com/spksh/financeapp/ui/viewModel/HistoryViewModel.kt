package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.ui.features.HistoryLoader
import com.spksh.financeapp.ui.features.multipleFetch
import com.spksh.financeapp.ui.state.HistoryScreenState
import com.spksh.financeapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Общая вьюмодель для экранов историй доходов и расходов
 */
open class HistoryViewModel(
    private val getTodayUseCase: GetTodayUseCase,
    private val historyLoader: HistoryLoader,
    private val isIncome: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HistoryScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HistoryScreenState>> = _uiState

    init {
        fetchDataDefault()
    }

    fun fetchData(
        startDate: LocalDate,
        endDate: LocalDate
    ) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        try {
            multipleFetch(
                fetch = {
                    _uiState.value = historyLoader.load(isIncome, startDate, endDate)
                }
            )
        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }

    fun fetchDataDefault() {
        val today = getTodayUseCase()
        fetchData(
            startDate = today.withDayOfMonth(1),
            endDate = today
        )
    }

    fun changeStartDate(newDate: Long?) {
        newDate?.let {
            val startDate = Instant
                .ofEpochMilli(it)
                .atOffset(ZoneOffset.UTC)
                .toLocalDate()
            fetchData(
                startDate = startDate,
                endDate = if (uiState.value is UiState.Success<HistoryScreenState>) {
                    (uiState.value as UiState.Success<HistoryScreenState>).data.endDate
                } else {
                    startDate
                }
            )
        }
    }

    fun changeEndDate(newDate: Long?) {
        newDate?.let {
            val endDate = Instant
                .ofEpochMilli(it)
                .atOffset(ZoneOffset.UTC)
                .toLocalDate()
            fetchData(
                startDate = if (uiState.value is UiState.Success<HistoryScreenState>) {
                    (uiState.value as UiState.Success<HistoryScreenState>).data.startDate
                } else {
                    endDate
                },
                endDate = endDate
            )
        }
    }
}