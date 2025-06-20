package com.spksh.financeapp.ui.state

import com.spksh.financeapp.ui.model.AccountUIModel

sealed class AccountUiState {
    object Loading : AccountUiState()

    data class Error(val message: String) : AccountUiState()

    data class Success(val accounts: List<AccountUIModel>) : AccountUiState()
}