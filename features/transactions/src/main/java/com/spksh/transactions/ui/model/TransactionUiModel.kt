package com.spksh.transactions.ui.model

import java.time.Instant

data class TransactionUiModel(
    val id: Long = 0,
    val accountId: Long = 0,
    val categoryId: Long = 0,
    val amount: Double = 0.0,
    val transactionDate: Instant = Instant.MIN,
    val comment: String? = null,
    val createdAt: String = "",
    val updatedAt: String = ""
)