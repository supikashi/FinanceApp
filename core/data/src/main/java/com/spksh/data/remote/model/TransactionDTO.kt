package com.spksh.data.remote.model

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
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toDoubleOrNull() ?: 0.0,
        transactionDate = Instant.parse(transactionDate),
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}