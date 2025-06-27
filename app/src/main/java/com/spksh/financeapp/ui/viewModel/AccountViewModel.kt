package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
import com.spksh.financeapp.ui.features.multipleFetch
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.AccountScreenState
import com.spksh.financeapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана счета
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AccountScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<AccountScreenState>> = _uiState

    init {
        fetchAccount()
    }

    fun fetchAccount() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        try {
            multipleFetch(
                fetch = {
                    val account = getAccountsUseCase().map { it.toUiModel() }
                    _uiState.value = UiState.Success(AccountScreenState(account))
                }
            )
        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }
}