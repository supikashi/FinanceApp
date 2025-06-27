package com.spksh.financeapp.ui.viewModel

import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.ui.features.HistoryLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

/**
 * Наследник [HistoryViewModel] для экрана истории доходов
 */
@HiltViewModel
class IncomeHistoryViewModel @Inject constructor(
    getTodayUseCase: GetTodayUseCase,
    historyLoader: HistoryLoader,
) : HistoryViewModel(
    getTodayUseCase,
    historyLoader,
    true
)