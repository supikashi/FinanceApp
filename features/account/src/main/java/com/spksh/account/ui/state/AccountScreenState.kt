package com.spksh.account.ui.state

import com.spksh.graph.model.AccountPlotModel
import com.spksh.ui.model.AccountUIModel

/**
 * Модель для экрана счета
 */
data class AccountScreenState(
    val accounts: List<AccountUIModel>,
    val transactions: List<AccountPlotModel>
)
