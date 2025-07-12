package com.spksh.data.remote.model

import com.spksh.domain.model.TransactionResponse
import kotlinx.serialization.Serializable
import java.time.Instant
import kotlin.text.toDoubleOrNull

/**
 * DTO-модель транзакции, используемая для парсинга данных из API.
 */
@Serializable
data class TransactionResponseDTO(
    val id: Long = 0,
    val account: AccountDTO = AccountDTO(),
    val category: CategoryDTO = CategoryDTO(),
    val amount: String = "",
    val transactionDate: String = "",
    val comment: String? = null,
    val createdAt: String = "",
    val updatedAt: String = "",
) {
    fun toTransactionResponse() = TransactionResponse(
        id = id,
        account = account.toAccount(),
        category = category.toCategory(),
        amount = amount.toDoubleOrNull() ?: 0.0,
        transactionDate = Instant.parse(transactionDate),
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}