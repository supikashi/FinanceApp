package com.spksh.transactions.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.domain.model.Account
import com.spksh.transactions.ui.utils.TransactionLoader
import com.spksh.transactions.ui.state.TodayTransactionsScreenState
import com.spksh.ui.state.UiState
import com.spksh.ui.utils.multipleFetch
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * Вьюмодель для экрана доходов
 */

class SpendingViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val transactionLoader: TransactionLoader
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<TodayTransactionsScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<TodayTransactionsScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
        Log.i("my_tag", "spending init")
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                fetchData(accounts)
            }
        }
    }

    fun retryLoad() {
        viewModelScope.launch {
            loadAccountsUseCase()
            fetchData(accountsFlow.value)
        }
    }

    fun fetchData(accountsList: List<Account>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                multipleFetch(
                    fetch = {
                        _uiState.value =
                            transactionLoader.load(accountsList = accountsList, isIncome = false)
                    }
                )
            } catch (e: CancellationException) {
            } catch (e: Throwable) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

}