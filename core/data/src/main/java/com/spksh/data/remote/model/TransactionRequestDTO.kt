package com.spksh.data.remote.model

import com.spksh.domain.model.TransactionRequest
import kotlinx.serialization.Serializable
import java.time.Instant
import kotlin.Long
import kotlin.text.toDoubleOrNull

@Serializable
data class TransactionRequestDTO(
    val accountId: Long,
    val categoryId: Long,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
) {
    fun toTransactionRequest() = TransactionRequest(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toDoubleOrNull() ?: 0.0,
        transactionDate = Instant.parse(transactionDate),
        comment = comment,
    )
}

fun TransactionRequest.toDTO() = TransactionRequestDTO(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toString(),
    transactionDate = transactionDate.toString(),
    comment = comment,
)
