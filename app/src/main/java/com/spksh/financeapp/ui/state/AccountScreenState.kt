package com.spksh.financeapp.ui.state

import com.spksh.financeapp.ui.model.AccountUIModel

/**
 * Модель для экрана счета
 */
data class AccountScreenState(
    val accounts: List<AccountUIModel>
)
