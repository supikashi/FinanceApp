package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.LoadAccountsUseCase
import com.spksh.financeapp.ui.utils.HistoryLoader
import com.spksh.financeapp.ui.utils.multipleFetch
import com.spksh.financeapp.ui.state.HistoryScreenState
import com.spksh.financeapp.ui.state.UiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    private val historyLoader: HistoryLoader,
    private val isIncome: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HistoryScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<HistoryScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                (_uiState.value as? UiState.Success<HistoryScreenState>)?.data?.let {
                    fetchData(accounts, it.startDate, it.endDate)
                } ?: run {
                    fetchDataDefault(accounts)
                }
            }
        }
    }

    fun retryLoad() {
        viewModelScope.launch {
            loadAccountsUseCase()
        }
    }

    fun fetchData(
        accountsList: List<Account>,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                multipleFetch(
                    fetch = {
                        _uiState.value = historyLoader.load(accountsList, isIncome, startDate, endDate)
                    }
                )
            } catch (e: CancellationException) {
            } catch (e: Throwable) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchDataDefault(
        accountsList: List<Account>,
    ) {
        val today = getTodayUseCase()
        fetchData(
            accountsList = accountsList,
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
                accountsList = accountsFlow.value,
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
                accountsList = accountsFlow.value,
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