package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.domain.useCase.LoadAccountsUseCase
import com.spksh.financeapp.domain.useCase.UpdateAccountUseCase
import com.spksh.financeapp.ui.model.AccountUpdateUiModel
import com.spksh.financeapp.ui.state.AccountUpdateScreenState
import com.spksh.financeapp.ui.state.UiState
import com.spksh.financeapp.ui.utils.multipleFetch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вьюмодель для экрана изменения счета
 */
@HiltViewModel
class AccountUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase
) : ViewModel() {
    val accountId: Long = savedStateHandle.get<Long>("id") ?: 0
    private val accountsFlow = getAccountsFlowUseCase()
    private val _uiState = MutableStateFlow<UiState<AccountUpdateScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<AccountUpdateScreenState>> = _uiState
    private var fetchJob: Job? = null
    private var updateJob: Job? = null

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

    fun fetchAccount(accountsList: List<Account>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                multipleFetch(
                    fetch = {
                        val account = accountsList
                            .find {it.id == accountId}
                            ?: throw Exception("account was not found")
                        _uiState.value = UiState.Success(
                            AccountUpdateScreenState(
                                AccountUpdateUiModel(
                                    name = account.name,
                                    balance = account.balance.toString(),
                                    currency = account.currency
                                )
                            )
                        )
                    }
                )
            } catch (e: Throwable) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateAccount(
        accountUpdateUiModel: AccountUpdateUiModel,
        popBackStack: () -> Unit,
        onError: () -> Unit,
    ) {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            try {
                updateAccountUseCase(
                    accountId = accountId,
                    updateData = accountUpdateUiModel.toAccountUpdateData()
                )
                if (isActive) {
                    popBackStack()
                }
            } catch (e: CancellationException) {
            } catch (e: Throwable) {
                onError()
            }
        }
    }

    fun localUpdateAccount(accountUpdateUiModel: AccountUpdateUiModel) {
        _uiState.value = UiState.Success(
            AccountUpdateScreenState(
                accountUpdateUiModel
            )
        )
    }
}