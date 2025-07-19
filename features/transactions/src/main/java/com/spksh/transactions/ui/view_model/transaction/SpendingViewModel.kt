package com.spksh.transactions.ui.view_model.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.domain.model.Account
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.transactions.domain.use_case.GetTransactionsByPeriodFlowUseCase
import com.spksh.transactions.domain.use_case.LoadTransactionsUseCase
import com.spksh.transactions.ui.state.TodayTransactionsScreenState
import com.spksh.ui.state.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана доходов
 */

class SpendingViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    private val getTransactionsByPeriodFlowUseCase: GetTransactionsByPeriodFlowUseCase,
    private val zoneIdUseCase: GetZoneIdUseCase,
    private val loadTransactionsUseCase: LoadTransactionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<TodayTransactionsScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<TodayTransactionsScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
        Log.i("my_tag", "income init")
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                if (accounts.isEmpty()) {
                    fetchAccount()
                } else {
                    fetchData(accounts)
                }
            }
        }
    }

    private fun fetchAccount() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        val response = loadAccountsUseCase()
        if (response == null) {
            _uiState.value = UiState.Error("")
        }
    }

    private fun fetchTransactions(accountId: Long) = viewModelScope.launch {
//        _uiState.value = UiState.Loading
//        val response = loadTransactionsUseCase(accountId)
//        if (!response) {
//            _uiState.value = UiState.Error("")
//        }
    }

    fun retryLoad() {
        viewModelScope.launch {
            if (accountsFlow.value.isEmpty()) {
                loadAccountsUseCase()
            } else {
                accountsFlow.value.firstOrNull()?.let {
                    fetchTransactions(it.localId)
                }
            }
        }
    }

    fun fetchData(accountsList: List<Account>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (accountsList.isEmpty()) {
                _uiState.value = UiState.Error("")
            } else {
                val todayLocal = getTodayUseCase()
                val today = todayLocal.atStartOfDay(zoneIdUseCase()).toInstant().toEpochMilli()
                val tomorrow = todayLocal.plusDays(1).atStartOfDay(zoneIdUseCase()).toInstant().toEpochMilli() - 1
                Log.i("my_tag", today.toString())
                Log.i("my_tag", tomorrow.toString())
                getTransactionsByPeriodFlowUseCase(
                    accountsList.first().localId,
                    today,
                    tomorrow
                ).collect { transactions ->
                    _uiState.value = UiState.Success(
                        data = TodayTransactionsScreenState.getByTransactions(
                            currency = accountsList.first().currency,
                            transactionResponses = transactions.filter { !it.category.isIncome }
                        )
                    )
                }
            }
        }
    }
}