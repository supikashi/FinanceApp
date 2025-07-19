package com.spksh.transactions.ui.utils

import com.spksh.transactions.domain.use_case.GetTransactionsByPeriodFlowUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.model.Account
import com.spksh.domain.model.TransactionResponse
import com.spksh.transactions.ui.model.toUiModel
import com.spksh.transactions.ui.state.HistoryScreenState
import com.spksh.ui.state.UiState
import com.spksh.ui.utils.formatSum
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Отвечает за загрузку истории транзакций
 */
//class HistoryLoader @Inject constructor(
//    private val getZoneIdUseCase: GetZoneIdUseCase,
//    private val getTransactionsByPeriodFlowUseCase: GetTransactionsByPeriodFlowUseCase
//) {
//    suspend fun load(
//        accountsList: List<Account>,
//        isIncome: Boolean,
//        startDate: LocalDate,
//        endDate: LocalDate
//    ) : UiState.Success<HistoryScreenState> {
//        val zoneId = getZoneIdUseCase()
//        val account = accountsList.first()
//        val transactions = getTransactionsByPeriodFlowUseCase(
//            accountId = account.remoteId!!,
//            startDate = startDate,
//            endDate = endDate
//        ).filter { it.category.isIncome == isIncome }
//        //val transactions = emptyList<TransactionResponse>()
//        return UiState.Success(
//            data = HistoryScreenState(
//                startDate = startDate,
//                endDate = endDate,
//                startDateString = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
//                endDateString = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
//                sum = formatSum(transactions.sumOf { it.amount }, account.currency),
//                transactions = transactions
//                    .sortedBy { it.transactionDate }
//                    .map {it.toUiModel(zoneId)}
//            )
//        )
//    }
//}