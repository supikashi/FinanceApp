package com.spksh.transactions.ui.state

import com.spksh.transactions.ui.model.TransactionRequestUiModel
import com.spksh.ui.model.AccountUIModel
import com.spksh.ui.model.CategoryUIModel

data class TransactionScreenState(
    val transaction: TransactionRequestUiModel = TransactionRequestUiModel(),
    val transactionId: Long? = null,
    val categoryList: List<CategoryUIModel> = emptyList(),
    val accountList: List<AccountUIModel> = emptyList()
)
