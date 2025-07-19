package com.spksh.domain.model

import java.time.Instant

/**
 * Доменная модель транзакции
 */
data class TransactionResponse(
    val localId: Long = 0,
    val remoteId: Long? = null,
    val account: Account = Account(),
    val category: Category = Category(),
    val amount: Double = 0.0,
    val transactionDate: Instant = Instant.MIN,
    val comment: String? = null,
    val createdAt: String = "",
    val updatedAt: String = "",
)