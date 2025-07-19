package com.spksh.transactions.ui.view_model.analysis

import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.transactions.domain.use_case.GetCategoryAnalysisFlowUseCase
import jakarta.inject.Inject

class SpendingAnalysisViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    loadAccountsUseCase: LoadAccountsUseCase,
    getTodayUseCase: GetTodayUseCase,
    getZoneIdUseCase: GetZoneIdUseCase,
    getCategoryAnalysisFlowUseCase: GetCategoryAnalysisFlowUseCase
) : AnalysisViewModel(
    getAccountsFlowUseCase,
    loadAccountsUseCase,
    getTodayUseCase,
    getZoneIdUseCase,
    getCategoryAnalysisFlowUseCase,
    false
)