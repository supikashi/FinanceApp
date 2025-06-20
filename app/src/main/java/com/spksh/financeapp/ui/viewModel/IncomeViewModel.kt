package com.spksh.financeapp.ui.viewModel

import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.GetTransactionsByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    getTodayUseCase: GetTodayUseCase,
    getAccountsUseCase: GetAccountsUseCase,
    getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) : TransactionViewModel(
    getTodayUseCase,
    getAccountsUseCase,
    getTransactionsByPeriodUseCase,
    true
)