package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.ui.features.TransactionLoader
import com.spksh.financeapp.ui.features.multipleFetch
import com.spksh.financeapp.ui.state.TransactionScreenState
import com.spksh.financeapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана доходов
 */
@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val transactionLoader: TransactionLoader
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<TransactionScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<TransactionScreenState>> = _uiState

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        try {
            multipleFetch(
                fetch = {
                    _uiState.value = transactionLoader.load(isIncome = true)
                }
            )
        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }
}