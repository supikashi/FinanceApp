package com.spksh.data.remote.model

import com.spksh.domain.model.AccountUpdateData
import kotlinx.serialization.Serializable

/**
 * DTO-модель для обновления данных счета
 */
@Serializable
data class AccountUpdateDTO(
    val name: String = "",
    val balance: String = "",
    val currency: String = ""
)

fun AccountUpdateData.toUpdateDTO() = AccountUpdateDTO(
    name = name,
    balance = balance.toString(),
    currency = currency
)