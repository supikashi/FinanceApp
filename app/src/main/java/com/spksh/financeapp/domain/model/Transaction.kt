package com.spksh.financeapp.domain.model

import java.time.Instant

data class Transaction(
    val id: Long = 0,
    val account: Account = Account(),
    val category: Category = Category(),
    val amount: Double = 0.0,
    val transactionDate: Instant = Instant.MIN,
    val comment: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)