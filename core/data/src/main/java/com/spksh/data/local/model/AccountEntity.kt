package com.spksh.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spksh.domain.model.Account

@Entity(tableName = "account_table")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val remoteId: Long? = null,
    val userId: Long = 0,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val isSync: Boolean = true
) {
    fun toAccount() = Account(
        localId = localId,
        remoteId = remoteId,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Account.toEntity() = AccountEntity(
    localId = localId,
    remoteId = remoteId,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)