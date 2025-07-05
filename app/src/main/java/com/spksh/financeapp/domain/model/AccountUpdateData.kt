package com.spksh.financeapp.domain.model

/**
 * Доменная модель изменения счета
 */
data class AccountUpdateData(
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = ""
)
