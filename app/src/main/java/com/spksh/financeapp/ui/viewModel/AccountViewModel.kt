package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.domain.useCase.LoadAccountsUseCase
import com.spksh.financeapp.domain.useCase.UpdateAccountUseCase
import com.spksh.financeapp.ui.model.AccountUIModel
import com.spksh.financeapp.ui.utils.multipleFetch
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.AccountScreenState
import com.spksh.financeapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана счета
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AccountScreenState>>(UiState.Loading)
    private val accountsFlow = getAccountsFlowUseCase()
    val uiState: StateFlow<UiState<AccountScreenState>> = _uiState

    init {
        viewModelScope.launch {
            accountsFlow.collect {
                fetchAccount(it)
            }
        }
    }

    fun retryLoad() {
        viewModelScope.launch {
            loadAccountsUseCase()
        }
    }

    fun fetchAccount(accountsList: List<Account>) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        try {
            multipleFetch(
                fetch = {
                    val account = accountsList.map { it.toUiModel() }
                    if (account.isEmpty()) {
                        _uiState.value =  UiState.Error("")
                    } else {
                        _uiState.value = UiState.Success(AccountScreenState(account))
                    }
                }
            )
        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }

    fun updateAccount(account: AccountUIModel) {
        //updateAccountUseCase(account.)
    }
}