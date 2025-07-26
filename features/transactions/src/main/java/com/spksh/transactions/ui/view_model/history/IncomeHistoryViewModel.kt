package com.spksh.transactions.ui.view_model.history

import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.domain.useCase.GetTransactionsByPeriodFlowUseCase
import jakarta.inject.Inject

/**
 * Наследник [HistoryViewModel] для экрана истории доходов
 */

class IncomeHistoryViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    loadAccountsUseCase: LoadAccountsUseCase,
    getTodayUseCase: GetTodayUseCase,
    getZoneIdUseCase: GetZoneIdUseCase,
    getTransactionsByPeriodFlowUseCase: GetTransactionsByPeriodFlowUseCase,
) : HistoryViewModel(
    getAccountsFlowUseCase,
    loadAccountsUseCase,
    getTodayUseCase,
    getZoneIdUseCase,
    getTransactionsByPeriodFlowUseCase,
    true
)