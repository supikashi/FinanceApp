package com.spksh.transactions.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.financeapp.ui.R
import com.spksh.transactions.ui.navigation.IncomeHistory
import com.spksh.transactions.ui.navigation.IncomeTransaction
import com.spksh.transactions.ui.view_model.transaction.IncomeViewModel
import com.spksh.transactions.ui.state.TodayTransactionsScreenState

import com.spksh.ui.state.UiState
import com.spksh.ui.components.AddButton
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.ScreenStateHandler
import com.spksh.ui.components.TopBar

@Composable
fun IncomeScreen(
    viewModel: IncomeViewModel,
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    IncomeScreenImpl(
        state = state,
        onHistoryIconClick = {
            navController.navigate(IncomeHistory) {
                launchSingleTop = true
                restoreState = true
            }
        },
        onRetryClick = {viewModel.retryLoad()},
        addButtonOnClick = {
            navController.navigate(IncomeTransaction(null)) {
                launchSingleTop = true
                restoreState = true
            }
        },
        onTransactionClick = {
            navController.navigate(IncomeTransaction(it)) {
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
private fun IncomeScreenImpl(
    state: UiState<TodayTransactionsScreenState>,
    onHistoryIconClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    addButtonOnClick: () -> Unit = {},
    onTransactionClick: (Long) -> Unit = {},
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = stringResource(R.string.income_today),
                rightIcon = R.drawable.update,
                rightIconOnClick = {onHistoryIconClick()},
                rightIconContentDescription = stringResource(R.string.show_history)
            )
            ScreenStateHandler(
                state = state,
                content = { IncomeScreenSuccess(state = it, onTransactionClick = onTransactionClick) },
                onRetryClick = onRetryClick,
            )
        }
        AddButton(
            onCLick = {
                addButtonOnClick()
            }
        )
    }
}

@Composable
private fun IncomeScreenSuccess(
    state: UiState.Success<TodayTransactionsScreenState>,
    onTransactionClick: (Long) -> Unit = {},
) {
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
                text = stringResource(R.string.whole),
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
        items(state.data.transactions) { transaction ->
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
                    Text(
                        text = transaction.amount,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

//@Preview
//@Composable
//fun IncomeScreenPreview() {
//    IncomeScreenImpl(
//        state = UiState.Success(
//            data = TransactionScreenState(
//                sum = MockData.incomeSumText,
//                categories = MockData.incomeCategoriesList
//            )
//        )
//    )
//}
