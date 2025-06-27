package com.spksh.financeapp.data.remote.model

import com.spksh.financeapp.domain.model.Account
import kotlinx.serialization.Serializable

/**
 * DTO-модель аккаунта, используемая для парсинга данных из API.
 */
@Serializable
data class AccountDTO(
    val id: Long = 0,
    val userId: Long = 0,
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
) {
    fun toAccount() = Account(
        id = id,
        userId = userId,
        name = name,
        balance = balance.toDoubleOrNull() ?: 0.0,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
