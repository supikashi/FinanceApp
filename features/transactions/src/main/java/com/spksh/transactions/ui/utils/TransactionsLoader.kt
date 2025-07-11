package com.spksh.transactions.ui.utils

import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.transactions.domain.use_case.GetTransactionsByPeriodUseCase
import com.spksh.domain.model.Account
import com.spksh.transactions.ui.state.TodayTransactionsScreenState
import com.spksh.ui.state.UiState
import jakarta.inject.Inject

/**
 * Отвечает за загрузку транзакций за сегодня
 */
class TransactionLoader @Inject constructor(
    private val getTodayUseCase: GetTodayUseCase,
    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
) {
    suspend fun load(accountsList: List<Account>, isIncome: Boolean): UiState.Success<TodayTransactionsScreenState> {
        val account = accountsList.first()
        val today = getTodayUseCase()
        return UiState.Success(
            data = TodayTransactionsScreenState.getByTransactions(
                account.currency,
                getTransactionsByPeriodUseCase(account.id, today, today)
                    .filter { it.category.isIncome == isIncome }
            )
        )
    }
}