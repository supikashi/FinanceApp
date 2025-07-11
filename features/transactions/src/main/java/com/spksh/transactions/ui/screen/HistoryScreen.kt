package com.spksh.transactions.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.financeapp.ui.R
import com.spksh.transactions.ui.state.HistoryScreenState
import com.spksh.transactions.ui.view_model.HistoryViewModel
import com.spksh.ui.state.UiState
import com.spksh.ui.components.DatePickerWrap
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.ScreenStateHandler
import com.spksh.ui.components.TopBar
import java.time.LocalDate

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import com.spksh.transactions.ui.navigation.IncomeTransaction
import com.spksh.transactions.ui.navigation.SpendingTransaction
import com.spksh.transactions.ui.view_model.IncomeHistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.retryLoad()
    }
    HistoryScreenImpl(
        state = state,
        onBackClick = { navController.popBackStack() },
        onStartDateSelected = { viewModel.changeStartDate(it) },
        onEndDateSelected = { viewModel.changeEndDate(it) },
        onRetryClick = { viewModel.retryLoad() },
        onTransactionClick = {
            navController.navigate(
                if (viewModel is IncomeHistoryViewModel)
                    IncomeTransaction(it)
                else
                    SpendingTransaction(it)
            ) {
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
private fun HistoryScreenImpl(
    state: UiState<HistoryScreenState>,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
    onTransactionClick: (Long) -> Unit = {},
    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = "Моя история",
                rightIcon = R.drawable.history,
                rightIconOnClick = {},
                rightIconContentDescription = null,
                leftIcon = R.drawable.arrow_back,
                leftIconOnClick = onBackClick,
                leftIconContentDescription = null,
            )
            ScreenStateHandler(
                state = state,
                content = {
                    HistoryScreenSuccess(
                        state = it,
                        onStartDateSelected = onStartDateSelected,
                        onEndDateSelected = onEndDateSelected,
                        onTransactionClick = onTransactionClick
                    )
                },
                onRetryClick = onRetryClick
            )
        }
    }
}

@Composable
private fun HistoryScreenSuccess(
    state: UiState.Success<HistoryScreenState>,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
    onTransactionClick: (Long) -> Unit = {},
) {
    DatePickListItem(
        leadingText = "Начало",
        dateText = state.data.startDateString,
        onDateSelected = onStartDateSelected
    )
    HorizontalDivider()
    DatePickListItem(
        leadingText = "Конец",
        dateText = state.data.endDateString,
        onDateSelected = onEndDateSelected
    )
    HorizontalDivider()
    ListItem(
        showTrailIcon = false,
        minHeight = 56.dp,
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Сумма",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = state.data.sum,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    HorizontalDivider()
    LazyColumn {
        items(state.data.transactions, {it.id}) { transaction ->
            ListItem(
                leadIcon = transaction.category.emoji,
                isClickable = true,
                onClick = {
                    onTransactionClick(transaction.id)
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = transaction.category.name,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        transaction.comment?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.outline,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = transaction.amount,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = transaction.transactionDate,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun DatePickListItem(
    leadingText: String = "",
    dateText: String = "",
    onDateSelected: (Long?) -> Unit = {},
) {
    var showDatePicker by remember { mutableStateOf(false) }
    ListItem(
        showTrailIcon = false,
        minHeight = 56.dp,
        containerColor = MaterialTheme.colorScheme.secondary,
        isClickable = true,
        onClick = {showDatePicker = true}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = leadingText,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    if (showDatePicker) {
        DatePickerWrap(
            onDateSelected = { dateState ->
                onDateSelected(dateState)
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

//@Preview
//@Composable
//fun HistoryScreenPreview() {
//    HistoryScreenImpl(
//        state = UiState.Success(
//            data = HistoryScreenState(
//                startDate = LocalDate.MIN,
//                endDate = LocalDate.MIN,
//                startDateString = MockData.historyStartDate,
//                endDateString = MockData.historyEndDate,
//                sum = MockData.historySum,
//                transactions = MockData.transactions,
//            )
//        )
//    )
//}