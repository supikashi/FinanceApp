package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.GetTransactionsByPeriodUseCase
import com.spksh.financeapp.domain.useCase.GetZoneIdUseCase
import com.spksh.financeapp.ui.features.formatCurrency
import com.spksh.financeapp.ui.features.formatDouble
import com.spksh.financeapp.ui.features.multipleFetch
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.HistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

open class HistoryViewModel(
    private val getTodayUseCase: GetTodayUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase,
    private val isIncome: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState

    init {
        fetchDataDefault()
    }

    fun fetchData(
        startDate: LocalDate,
        endDate: LocalDate
    ) = viewModelScope.launch {
        _uiState.value = HistoryUiState.Loading
        try {
            multipleFetch(
                fetch = {
                    val zoneId = getZoneIdUseCase()
                    val account = getAccountsUseCase().first()
                    val transactions = getTransactionsByPeriodUseCase(
                        accountId = account.id,
                        startDate = startDate,
                        endDate = endDate
                    ).filter { it.category.isIncome == isIncome }
                    _uiState.value = HistoryUiState.Success(
                        startDate = startDate,
                        endDate = endDate,
                        startDateString = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        endDateString = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        sum = "${formatDouble(transactions.sumOf { it.amount })} ${formatCurrency(account.currency)}",
                        transactions = transactions
                            .sortedBy { it.transactionDate }
                            .map {it.toUiModel(zoneId)}
                    )
                }
            )
        } catch (e: Throwable) {
            _uiState.value = HistoryUiState.Error(e.message ?: "Unknown error")
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
                endDate = when(uiState.value) {
                    is HistoryUiState.Success -> {
                        (uiState.value as HistoryUiState.Success).endDate
                    }
                    is HistoryUiState.Loading -> {
                        getTodayUseCase()
                    }
                    is HistoryUiState.Error -> {
                        getTodayUseCase()
                    }
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
                startDate = when(uiState.value) {
                    is HistoryUiState.Success -> {
                        (uiState.value as HistoryUiState.Success).startDate
                    }
                    is HistoryUiState.Loading -> {
                        getTodayUseCase()
                    }
                    is HistoryUiState.Error -> {
                        getTodayUseCase()
                    }
                },
                endDate = endDate
            )
        }
    }
}