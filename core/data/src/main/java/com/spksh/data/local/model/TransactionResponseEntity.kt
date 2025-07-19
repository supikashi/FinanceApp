package com.spksh.data.local.model


import androidx.room.Embedded
import androidx.room.Relation
import com.spksh.domain.model.TransactionResponse
import java.time.Instant

data class TransactionResponseEntity(
    @Embedded val transactionEntity: TransactionEntity,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "localId"
    )
    val account: AccountEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity,
) {
    fun toTransactionResponse() = TransactionResponse(
        localId = transactionEntity.localId,
        remoteId = transactionEntity.remoteId,
        account = account.toAccount(),
        category = category.toCategory(),
        amount = transactionEntity.amount,
        transactionDate = Instant.ofEpochMilli(transactionEntity.transactionDate),
        comment = transactionEntity.comment,
        createdAt = transactionEntity.createdAt,
        updatedAt = transactionEntity.updatedAt
    )
}