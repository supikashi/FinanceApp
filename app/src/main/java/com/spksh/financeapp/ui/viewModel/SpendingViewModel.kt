package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.domain.useCase.LoadAccountsUseCase
import com.spksh.financeapp.ui.utils.TransactionLoader
import com.spksh.financeapp.ui.utils.multipleFetch
import com.spksh.financeapp.ui.state.TransactionScreenState
import com.spksh.financeapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана доходов
 */
@HiltViewModel
class SpendingViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val transactionLoader: TransactionLoader
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<TransactionScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<TransactionScreenState>> = _uiState
    private var fetchJob: Job? = null

    init {
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
                        _uiState.value = transactionLoader.load(accountsList = accountsList, isIncome = false)
                    }
                )
            } catch (e: CancellationException) {
            } catch (e: Throwable) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}