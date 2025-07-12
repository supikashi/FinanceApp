package com.spksh.domain.model

import java.time.Instant

data class TransactionRequest(
    val accountId: Long,
    val categoryId: Long,
    val amount: Double = 0.0,
    val transactionDate: Instant = Instant.MIN,
    val comment: String?,
)