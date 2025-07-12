package com.spksh.transactions.ui.view_model

import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.transactions.ui.utils.HistoryLoader
import jakarta.inject.Inject

/**
 * Наследник [HistoryViewModel] для экрана истории доходов
 */

class IncomeHistoryViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    loadAccountsUseCase: LoadAccountsUseCase,
    getTodayUseCase: GetTodayUseCase,
    historyLoader: HistoryLoader,
) : HistoryViewModel(
    getAccountsFlowUseCase,
    loadAccountsUseCase,
    getTodayUseCase,
    historyLoader,
    true
)