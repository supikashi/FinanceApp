package com.spksh.account.ui.model

import com.spksh.domain.model.AccountUpdateData

/**
 * UI-модель изменения счета
 */
data class AccountUpdateUiModel(
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
) {
    fun toAccountUpdateData() = AccountUpdateData(
        name = name,
        balance = balance.toDouble(),
        currency = currency
    )
}

fun AccountUpdateData.toUiModel() = AccountUpdateUiModel(
    name = name,
    balance = balance.toString(),
    currency = currency
)