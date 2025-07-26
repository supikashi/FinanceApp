package com.spksh.account.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.account.domain.use_case.SyncAccountUseCase
import com.spksh.account.ui.state.AccountScreenState
import com.spksh.domain.model.Account
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetTransactionsByPeriodFlowUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.graph.model.AccountPlotModel
import com.spksh.ui.model.toUiModel
import com.spksh.ui.state.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.SortedMap

/**
 * Вьюмодель для экрана счета
 */

class AccountViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val syncAccountUseCase: SyncAccountUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
    private val getTransactionsByPeriodFlowUseCase: GetTransactionsByPeriodFlowUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AccountScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<AccountScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                Log.i("my_tag", "account collected")
                if (accounts.isEmpty()) {
                    fetchAccount()
                } else {
                    fetchData(accounts)
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

    fun fetchData(
        accountsList: List<Account>
    ) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (accountsList.isEmpty()) {
                _uiState.value = UiState.Error("")
            } else {
                val zoneId = getZoneIdUseCase()
                val today = getTodayUseCase()
                getTransactionsByPeriodFlowUseCase(
                    accountsList.first().localId,
                    today.withDayOfMonth(1).atStartOfDay(zoneId).toInstant().toEpochMilli(),
                    today.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1
                ).collect { transactions ->
                    val map = sortedMapOf<Int, Double>()
                    val lastDayInMonth = today
                        .plusMonths(1)
                        .withDayOfMonth(1)
                        .minusDays(1)
                        .dayOfMonth
                    for (i in 1..lastDayInMonth) {
                        map[i] = 0.0
                    }
                    transactions.forEach {  transaction ->
                        val day = transaction.transactionDate.atZone(zoneId).dayOfMonth
                        if (transaction.category.isIncome) {
                            map[day] = (map[day] ?: 0.0) + transaction.amount
                        } else {
                            map[day] = (map[day] ?: 0.0) - transaction.amount
                        }

                    }

                    _uiState.value = UiState.Success(
                        data = AccountScreenState(
                            accounts = accountsList.map {it.toUiModel()},
                            transactions = map.toList().map {
                                AccountPlotModel(it.second.toFloat(), it.first.toString())
                            }
                        )
                    )
                }
            }
        }
    }

    fun syncAccount() = viewModelScope.launch {
        try {
            syncAccountUseCase()
        } catch (e: Exception) {
            Log.i("my_tag", e.message ?: "sync error")
        }
    }
}