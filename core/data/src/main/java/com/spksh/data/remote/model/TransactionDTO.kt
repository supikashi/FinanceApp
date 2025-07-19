package com.spksh.data.remote.model

import com.spksh.data.local.model.TransactionEntity
import com.spksh.domain.model.Transaction
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TransactionDTO(
    val id: Long = 0,
    val accountId: Long = 0,
    val categoryId: Long = 0,
    val amount: String = "",
    val transactionDate: String = "",
    val comment: String? = null,
    val createdAt: String = "",
    val updatedAt: String = ""
) {
    fun toTransaction() = Transaction(
        remoteId = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toDoubleOrNull() ?: 0.0,
        transactionDate = Instant.parse(transactionDate),
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun toEntity(localId: Long = 0) = TransactionEntity(
        localId = localId,
        remoteId = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toDoubleOrNull() ?: 0.0,
        transactionDate = Instant.parse(transactionDate).toEpochMilli(),
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}