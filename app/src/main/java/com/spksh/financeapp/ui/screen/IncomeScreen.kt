package com.spksh.financeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.spksh.financeapp.MockData
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.ErrorScreen
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.navigation.IncomeHistory
import com.spksh.financeapp.ui.state.HistoryUiState
import com.spksh.financeapp.ui.state.TransactionUiState
import com.spksh.financeapp.ui.viewModel.IncomeViewModel

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
        onRetryClick = {viewModel.fetchData()}
    )
}

@Composable
private fun IncomeScreenImpl(
    state: TransactionUiState,
    onHistoryIconClick: () -> Unit = {},
    onRetryClick: () -> Unit = {}
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
            when (state) {
                is TransactionUiState.Error -> {
                    ErrorScreen(
                        onRetryClick = onRetryClick
                    )
                }
                TransactionUiState.Loading -> {
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
                is TransactionUiState.Success -> {
                    IncomeScreenSuccess(
                        state = state
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
                .size(56.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            Icon(Icons.Default.Add, stringResource(R.string.add_new))
        }
    }
}

@Composable
private fun IncomeScreenSuccess(
    state: TransactionUiState.Success
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
                text = state.sum,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    HorizontalDivider()
    LazyColumn {
        items(state.categories) { category ->
            ListItem(
                leadIcon = category.emoji,
                isClickable = true
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = category.name,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        category.description?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.outline,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Text(
                        text = category.sum,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun IncomeScreenPreview() {
    IncomeScreenImpl(
        state = TransactionUiState.Success(
            sum = MockData.incomeSumText,
            categories = MockData.incomeCategoriesList
        )
    )
}
