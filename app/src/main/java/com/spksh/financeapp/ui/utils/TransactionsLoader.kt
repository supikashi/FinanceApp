package com.spksh.financeapp.ui.utils

import com.spksh.financeapp.domain.model.Account
import com.spksh.financeapp.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.domain.useCase.GetTodayUseCase
import com.spksh.financeapp.domain.useCase.GetTransactionsByPeriodUseCase
import com.spksh.financeapp.ui.state.TransactionScreenState
import com.spksh.financeapp.ui.state.UiState
import jakarta.inject.Inject

/**
 * Отвечает за загрузку транзакций за сегодня
 */
class TransactionLoader @Inject constructor(
    private val getTodayUseCase: GetTodayUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) {
    suspend fun load(accountsList: List<Account>, isIncome: Boolean): UiState.Success<TransactionScreenState> {
        val account = accountsList.first()
        val today = getTodayUseCase()
        return UiState.Success(
            data = TransactionScreenState.getByTransactions(
                account.currency,
                getTransactionsByPeriodUseCase(account.id, today, today)
                    .filter { it.category.isIncome == isIncome }
            )
        )
    }
}