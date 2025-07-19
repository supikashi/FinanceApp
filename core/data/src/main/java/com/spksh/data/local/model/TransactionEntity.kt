package com.spksh.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spksh.data.remote.model.TransactionRequestDTO
import com.spksh.domain.model.Transaction
import com.spksh.domain.model.TransactionRequest
import java.time.Instant

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val remoteId: Long? = null,
    val accountId: Long = 0,
    val categoryId: Long = 0,
    val amount: Double = 0.0,
    val transactionDate: Long = 0,
    val comment: String? = null,
    val createdAt: String = "",
    val updatedAt: String = "",
    val isSync: Boolean = true,
    val isDeleted: Boolean = false
) {
    fun toTransaction() = Transaction(
        localId = localId,
        remoteId = remoteId,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = Instant.ofEpochMilli(transactionDate),
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    fun toRequestDTO() = TransactionRequestDTO(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toString(),
        transactionDate = Instant.ofEpochMilli(transactionDate).toString(),
        comment = comment
    )
}