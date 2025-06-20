package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
import com.spksh.financeapp.ui.features.multipleFetch
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.AccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState

    init {
        fetchAccount()
    }

    fun fetchAccount() = viewModelScope.launch {
        _uiState.value = AccountUiState.Loading
        try {
            multipleFetch(
                fetch = {
                    val account = getAccountsUseCase().map { it.toUiModel() }
                    _uiState.value = AccountUiState.Success(account)
                }
            )
        } catch (e: Throwable) {
            _uiState.value = AccountUiState.Error(e.message ?: "Unknown error")
        }
    }
}