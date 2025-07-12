package com.spksh.transactions.ui.model

import com.spksh.domain.model.TransactionRequest
import java.time.LocalDate
import java.time.ZoneId

data class TransactionRequestUiModel(
    val accountId: Long = 0,
    val categoryId: Long = 0,
    val amount: String = "",
    val dateString: String = "",
    val timeString: String = "",
    val date: LocalDate = LocalDate.MIN,
    val time: Int = 0,
    val comment: String = "",
) {
    fun toTransactionRequest(zoneId: ZoneId) = TransactionRequest(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toDouble(),
        transactionDate = date.atTime(time / 60, time % 60).atZone(zoneId).toInstant(),
        comment = comment
    )
}
