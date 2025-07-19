package com.spksh.account.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.ui.model.toUiModel
import com.spksh.account.ui.state.AccountScreenState
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.domain.model.Account
import com.spksh.ui.state.UiState
import com.spksh.ui.utils.multipleFetch
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана счета
 */

class AccountViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AccountScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<AccountScreenState>> = _uiState

    init {
        viewModelScope.launch {
            accountsFlow.collect { accounts ->
                Log.i("my_tag", "account collected")
                if (accounts.isEmpty()) {
                    fetchAccount()
                } else {
                    _uiState.value = UiState.Success(
                        AccountScreenState(
                            accounts.map { it.toUiModel() }
                        )
                    )
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
}