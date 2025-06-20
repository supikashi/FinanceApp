package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.GetTransactionsByPeriodUseCase
import com.spksh.financeapp.ui.features.multipleFetch
import com.spksh.financeapp.ui.state.TransactionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class TransactionViewModel(
    private val getTodayUseCase: GetTodayUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase,
    private val isIncome: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.value = TransactionUiState.Loading
        try {
            multipleFetch(
                fetch = {
                    val account = getAccountsUseCase().first()
                    val today = getTodayUseCase()
                    _uiState.value = TransactionUiState
                        .getByTransactions(
                            account.currency,
                            getTransactionsByPeriodUseCase(account.id, today, today)
                                .filter { it.category.isIncome == isIncome }
                        )
                }
            )
        } catch (e: Throwable) {
            _uiState.value = TransactionUiState.Error(e.message ?: "Unknown error")
        }
    }
}