package com.spksh.transactions.ui.view_model.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.model.Account
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.transactions.domain.use_case.GetCategoryAnalysisFlowUseCase
import com.spksh.transactions.ui.model.toUiModel
import com.spksh.transactions.ui.state.AnalysisScreenState
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

open class AnalysisViewModel(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
    private val getCategoryAnalysisFlowUseCase: GetCategoryAnalysisFlowUseCase,
    private val isIncome: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AnalysisScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<AnalysisScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                if (accounts.isEmpty()) {
                    fetchAccount()
                } else {
                    (_uiState.value as? UiState.Success<AnalysisScreenState>)?.data?.let {
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
                getCategoryAnalysisFlowUseCase(
                    accountsList.first().localId,
                    startDate.atStartOfDay(zoneId).toInstant().toEpochMilli(),
                    endDate.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1,
                    isIncome
                ).collect { analysisData ->
                    val filtered = analysisData.categories.filter { it.isIncome == isIncome }
                    _uiState.value = UiState.Success(
                        data = AnalysisScreenState(
                            startDate = startDate,
                            endDate = endDate,
                            startDateString = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            endDateString = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            sum = formatSum(analysisData.sum, accountsList.first().currency),
                            categories = filtered
                                .sortedByDescending { it.sum }
                                .map { it.toUiModel(accountsList.first().currency) },
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
                endDate = if (uiState.value is UiState.Success<AnalysisScreenState>) {
                    (uiState.value as UiState.Success<AnalysisScreenState>).data.endDate
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
                startDate = if (uiState.value is UiState.Success<AnalysisScreenState>) {
                    (uiState.value as UiState.Success<AnalysisScreenState>).data.startDate
                } else {
                    endDate
                },
                endDate = endDate
            )
        }
    }
}