package com.spksh.financeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.spksh.financeapp.MockData
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.DatePickerWrap
import com.spksh.financeapp.ui.components.ErrorScreen
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.state.CategoryUiState
import com.spksh.financeapp.ui.state.HistoryUiState
import com.spksh.financeapp.ui.viewModel.HistoryViewModel
import java.time.LocalDate

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    HistoryScreenImpl(
        state = state,
        onBackClick = { navController.popBackStack() },
        onStartDateSelected = { viewModel.changeStartDate(it) },
        onEndDateSelected = { viewModel.changeEndDate(it) },
        onRetryClick = { viewModel.fetchDataDefault() }
    )
}

@Composable
private fun HistoryScreenImpl(
    state: HistoryUiState,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
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
            when (state) {
                is HistoryUiState.Error -> {
                    ErrorScreen(
                        onRetryClick = onRetryClick
                    )
                }
                HistoryUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(alignment = Alignment.Center)
                        )
                    }
                }
                is HistoryUiState.Success -> {
                    HistoryScreenSuccess(
                        state = state,
                        onStartDateSelected = onStartDateSelected,
                        onEndDateSelected = onEndDateSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryScreenSuccess(
    state: HistoryUiState.Success,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {}
) {
    DatePickListItem(
        leadingText = "Начало",
        dateText = state.startDateString,
        onDateSelected = onStartDateSelected
    )
    HorizontalDivider()
    DatePickListItem(
        leadingText = "Конец",
        dateText = state.endDateString,
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
                text = state.sum,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    HorizontalDivider()
    LazyColumn {
        items(state.transactions) { transaction ->
            ListItem(
                leadIcon = transaction.category.emoji,
                isClickable = true
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
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

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreenImpl(
        state = HistoryUiState.Success(
            startDate = LocalDate.MIN,
            endDate = LocalDate.MIN,
            startDateString = MockData.historyStartDate,
            endDateString = MockData.historyEndDate,
            sum = MockData.historySum,
            transactions = MockData.transactions,
        )
    )
}