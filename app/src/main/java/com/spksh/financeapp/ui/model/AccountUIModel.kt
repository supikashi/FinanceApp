package com.spksh.financeapp.ui.model

import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.ui.features.formatCurrency
import com.spksh.financeapp.ui.features.formatSum

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
    id = id,
    name = name,
    balance = formatSum(balance, currency),
    currency = formatCurrency(currency)
)