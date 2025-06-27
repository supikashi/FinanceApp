package com.spksh.financeapp.ui.features

import com.spksh.financeapp.domain.useCase.GetAccountsUseCase
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
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) {
    suspend fun load(isIncome: Boolean): UiState.Success<TransactionScreenState> {
        val account = getAccountsUseCase().first()
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