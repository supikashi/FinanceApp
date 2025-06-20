package com.spksh.financeapp.ui.viewModel

import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.GetTransactionsByPeriodUseCase
import com.spksh.financeapp.domain.useCase.GetZoneIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SpendingHistoryViewModel @Inject constructor(
    getTodayUseCase: GetTodayUseCase,
    getZoneIdUseCase: GetZoneIdUseCase,
    getAccountsUseCase: GetAccountsUseCase,
    getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) : HistoryViewModel(
    getTodayUseCase,
    getZoneIdUseCase,
    getAccountsUseCase,
    getTransactionsByPeriodUseCase,
    false
)