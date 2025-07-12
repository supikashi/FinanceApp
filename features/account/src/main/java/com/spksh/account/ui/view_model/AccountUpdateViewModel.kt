package com.spksh.account.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.account.ui.model.AccountUpdateUiModel
import com.spksh.account.ui.state.AccountUpdateScreenState
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.account.domain.use_case.UpdateAccountUseCase
import com.spksh.domain.model.Account
import com.spksh.ui.state.UiState
import com.spksh.ui.utils.multipleFetch
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

class AccountUpdateViewModel @Inject constructor(
    //savedStateHandle: SavedStateHandle,
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase
) : ViewModel() {
    //val accountId: Long = savedStateHandle.get<Long>("id") ?: 0
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
                            .find {it.id == accountsList.first().id}
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
        val accountId = accountsFlow.value.first().id
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            try {
                updateAccountUseCase(
                    accountId = accountsFlow.value.first().id,
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