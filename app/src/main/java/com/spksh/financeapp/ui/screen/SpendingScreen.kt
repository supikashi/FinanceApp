package com.spksh.financeapp.ui.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.financeapp.MockData
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.AddButton
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.ScreenStateHandler
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.navigation.SpendingHistory
import com.spksh.financeapp.ui.state.TransactionScreenState
import com.spksh.financeapp.ui.state.UiState
import com.spksh.financeapp.ui.viewModel.SpendingViewModel

@Composable
fun SpendingScreen(
    viewModel: SpendingViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    SpendingScreenImpl(
        state = state,
        onHistoryIconClick = {
            navController.navigate(SpendingHistory) {
                launchSingleTop = true
                restoreState = true
            }
        },
        onRetryClick = {viewModel.fetchData()}
    )
}

@Composable
fun SpendingScreenImpl(
    state: UiState<TransactionScreenState>,
    onHistoryIconClick: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = stringResource(R.string.expences_today),
                rightIcon = R.drawable.update,
                rightIconOnClick = {onHistoryIconClick()},
                rightIconContentDescription = stringResource(R.string.show_history)
            )
            ScreenStateHandler(
                state = state,
                content = { SpendingScreenSuccess(it) },
                onRetryClick = onRetryClick,
            )
        }
        AddButton(
            onCLick = {}
        )
    }
}

@Composable
fun SpendingScreenSuccess(
    state: UiState.Success<TransactionScreenState>
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
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    HorizontalDivider()
    LazyColumn {
        items(state.data.categories) { category ->
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
fun SpendingScreenPreview() {
    SpendingScreenImpl(
        state = UiState.Success(
            data = TransactionScreenState(
                sum = MockData.spendingSumText,
                categories = MockData.spendingCategoriesList
            )
        )
    )
}
