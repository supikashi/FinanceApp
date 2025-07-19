package com.spksh.ui.model

import com.spksh.domain.model.Account
import com.spksh.ui.utils.formatCurrency
import com.spksh.ui.utils.formatSum

/**
 * UI-модель счета
 */
data class AccountUIModel(
    val id: Long = 0,
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
    val changesList: List<AccountChangeUIModel> = emptyList()
)

fun Account.toUiModel() = AccountUIModel(
    id = localId,
    name = name,
    balance = formatSum(balance, currency),
    currency = formatCurrency(currency)
)