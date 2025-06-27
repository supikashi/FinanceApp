package com.spksh.financeapp.data.remote.model

import com.spksh.financeapp.domain.model.Transaction
import kotlinx.serialization.Serializable
import java.time.Instant

/**
 * DTO-модель транзакции, используемая для парсинга данных из API.
 */
@Serializable
data class TransactionDTO(
    val id: Long = 0,
    val account: AccountDTO = AccountDTO(),
    val category: CategoryDTO = CategoryDTO(),
    val amount: String = "",
    val transactionDate: String = "",
    val comment: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
) {
    fun toTransaction() = Transaction(
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