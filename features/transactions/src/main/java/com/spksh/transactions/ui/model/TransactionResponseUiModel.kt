package com.spksh.transactions.ui.model

import com.spksh.domain.model.TransactionResponse
import com.spksh.ui.model.CategoryUIModel
import com.spksh.ui.model.toUiModel
import com.spksh.ui.utils.formatSum
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * UI-модель транзакции
 */
data class TransactionResponseUiModel(
    val id: Long = 0,
    val category: CategoryUIModel = CategoryUIModel(),
    val amount: String = "",
    val transactionDate: String = "",
    val comment: String? = null,
)

fun TransactionResponse.toUiModel(zoneId: ZoneId? = null) : TransactionResponseUiModel {
    val formatter = if (zoneId != null) {
        DateTimeFormatter
            .ofPattern("MM-dd HH:mm")
            .withZone(zoneId)
    } else {
        null
    }
    return TransactionResponseUiModel(
        id = localId,
        category = category.toUiModel(),
        amount = formatSum(amount, account.currency),
        transactionDate = formatter?.format(transactionDate) ?: "",
        comment = if (comment == "") null else comment
    )
}