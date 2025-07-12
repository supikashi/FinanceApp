package com.spksh.ui.utils

//import com.spksh.domain.useCase.GetTransactionsByPeriodUseCase
//import com.spksh.domain.useCase.GetZoneIdUseCase
//import com.spksh.model.Account
//import com.spksh.financeapp.ui.model.toUiModel
//import com.spksh.financeapp.ui.state.HistoryScreenState
//import com.spksh.financeapp.ui.state.UiState
//import com.spksh.ui.state.UiState
//import jakarta.inject.Inject
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
///**
// * Отвечает за загрузку истории транзакций
// */
//class HistoryLoader @Inject constructor(
//    private val getZoneIdUseCase: GetZoneIdUseCase,
//    private val getTransactionsByPeriodUseCase: GetTransactionsByPeriodUseCase
//) {
//    suspend fun load(
//        accountsList: List<Account>,
//        isIncome: Boolean,
//        startDate: LocalDate,
//        endDate: LocalDate
//    ) : UiState.Success<HistoryScreenState> {
//        val zoneId = getZoneIdUseCase()
//        val account = accountsList.first()
//        val transactions = getTransactionsByPeriodUseCase(
//            accountId = account.id,
//            startDate = startDate,
//            endDate = endDate
//        ).filter { it.category.isIncome == isIncome }
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
