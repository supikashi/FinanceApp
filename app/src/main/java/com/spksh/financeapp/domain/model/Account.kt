package com.spksh.financeapp.domain.model

/**
 * Доменная модель счета
 */
data class Account(
    val id: Long = 0,
    val userId: Long = 0,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)