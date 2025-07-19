package com.spksh.domain.model

/**
 * Доменная модель счета
 */
data class Account(
    val localId: Long = 0,
    val remoteId: Long? = null,
    val userId: Long = 0,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)