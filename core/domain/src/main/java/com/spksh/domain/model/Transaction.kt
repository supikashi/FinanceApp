package com.spksh.domain.model

import java.time.Instant

data class Transaction(
    val localId: Long = 0,
    val remoteId: Long? = null,
    val accountId: Long = 0,
    val categoryId: Long = 0,
    val amount: Double = 0.0,
    val transactionDate: Instant = Instant.MIN,
    val comment: String? = null,
    val createdAt: String = "",
    val updatedAt: String = ""
)
