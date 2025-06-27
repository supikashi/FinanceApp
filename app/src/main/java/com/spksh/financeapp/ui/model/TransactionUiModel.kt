package com.spksh.financeapp.ui.model

import com.spksh.financeapp.domain.model.Transaction
import com.spksh.financeapp.ui.features.formatSum
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * UI-модель транзакции
 */
data class TransactionUiModel(
    val id: Long = 0,
    val category: CategoryUIModel = CategoryUIModel(),
    val amount: String = "",
    val transactionDate: String = "",
    val comment: String? = null,
)

fun Transaction.toUiModel(zoneId: ZoneId) : TransactionUiModel {
    val formatter = DateTimeFormatter
        .ofPattern("MM-dd HH:mm")
        .withZone(zoneId)
    return TransactionUiModel(
        id = id,
        category = category.toUiModel(),
        amount = formatSum(amount, account.currency),
        transactionDate = formatter.format(transactionDate),
        comment = if (comment == "") null else comment
    )
}