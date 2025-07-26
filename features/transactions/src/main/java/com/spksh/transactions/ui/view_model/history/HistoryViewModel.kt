package com.spksh.transactions.ui.view_model.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.domain.model.Account
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.GetTransactionsByPeriodFlowUseCase
import com.spksh.transactions.ui.model.toUiModel
import com.spksh.transactions.ui.state.HistoryScreenState
import com.spksh.ui.state.UiState
import com.spksh.ui.utils.formatSum
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Общая вьюмодель для экранов историй доходов и расходов
 */
open class HistoryViewModel(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
    private val getTransactionsByPeriodFlowUseCase: GetTransactionsByPeriodFlowUseCase,
    private val isIncome: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<HistoryScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<HistoryScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                if (accounts.isEmpty()) {
                    fetchAccount()
                } else {
                    (_uiState.value as? UiState.Success<HistoryScreenState>)?.data?.let {
                        fetchData(accounts, it.startDate, it.endDate)
                    } ?: run {
                        fetchDataDefault(accounts)
                    }
                }
            }
        }
    }

    fun fetchAccount() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        val response = loadAccountsUseCase()
        if (response == null) {
            _uiState.value = UiState.Error("")
        }
    }

    fun retryLoad() {
        viewModelScope.launch {
            loadAccountsUseCase()
            fetchDataDefault(accountsFlow.value)
        }
    }

    fun fetchData(
        accountsList: List<Account>,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (accountsList.isEmpty()) {
                _uiState.value = UiState.Error("")
            } else {
                val zoneId = getZoneIdUseCase()
                getTransactionsByPeriodFlowUseCase(
                    accountsList.first().localId,
                    startDate.atStartOfDay(zoneId).toInstant().toEpochMilli(),
                    endDate.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1
                ).collect { transactions ->
                    val filtered = transactions.filter { it.category.isIncome == isIncome }
                    _uiState.value = UiState.Success(
                        data = HistoryScreenState(
                            startDate = startDate,
                            endDate = endDate,
                            startDateString = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            endDateString = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            sum = formatSum(filtered.sumOf { it.amount }, accountsList.first().currency),
                            transactions = filtered
                                .sortedBy { it.transactionDate }
                                .map {it.toUiModel(zoneId)}
                        )
                    )
                }
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