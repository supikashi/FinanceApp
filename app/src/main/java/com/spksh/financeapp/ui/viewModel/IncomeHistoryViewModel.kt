package com.spksh.financeapp.ui.viewModel

import com.spksh.financeapp.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.LoadAccountsUseCase
import com.spksh.financeapp.ui.utils.HistoryLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

/**
 * Наследник [HistoryViewModel] для экрана истории доходов
 */
@HiltViewModel
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