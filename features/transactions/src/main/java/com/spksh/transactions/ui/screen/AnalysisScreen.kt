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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.financeapp.ui.R
import com.spksh.transactions.ui.state.AnalysisScreenState
import com.spksh.ui.state.UiState
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.ScreenStateHandler
import com.spksh.ui.components.TopBar
import androidx.compose.foundation.lazy.items
import com.spksh.transactions.ui.view_model.analysis.AnalysisViewModel

@Composable
fun AnalysisScreen(
    viewModel: AnalysisViewModel,
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    AnalysisScreenImpl(
        state = state,
        onBackClick = { navController.popBackStack() },
        onStartDateSelected = { viewModel.changeStartDate(it) },
        onEndDateSelected = { viewModel.changeEndDate(it) },
        onRetryClick = { viewModel.retryLoad() },
    )
}

@Composable
private fun AnalysisScreenImpl(
    state: UiState<AnalysisScreenState>,
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
                headline = "Анализ",
                rightIconOnClick = {},
                rightIconContentDescription = null,
                leftIcon = R.drawable.arrow_back,
                leftIconOnClick = onBackClick,
                leftIconContentDescription = null,
                containerColor = MaterialTheme.colorScheme.background
            )
            ScreenStateHandler(
                state = state,
                content = {
                    AnalysisScreenSuccess(
                        state = it,
                        onStartDateSelected = onStartDateSelected,
                        onEndDateSelected = onEndDateSelected,
                    )
                },
                onRetryClick = onRetryClick
            )
        }
    }
}

@Composable
private fun AnalysisScreenSuccess(
    state: UiState.Success<AnalysisScreenState>,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
) {
    DatePickListItem(
        leadingText = "Начало",
        dateText = state.data.startDateString,
        onDateSelected = onStartDateSelected,
        backgroundColor = MaterialTheme.colorScheme.background,
        buttonColor = MaterialTheme.colorScheme.primary
    )
    HorizontalDivider()
    DatePickListItem(
        leadingText = "Конец",
        dateText = state.data.endDateString,
        onDateSelected = onEndDateSelected,
        backgroundColor = MaterialTheme.colorScheme.background,
        buttonColor = MaterialTheme.colorScheme.primary
    )
    HorizontalDivider()
    ListItem(
        showTrailIcon = false,
        minHeight = 56.dp
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
    if (state.data.categories.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Транзакции за данный период отсутствуют",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn {
            items(state.data.categories, {it.id}) { categories ->
                ListItem(
                    leadIcon = categories.emoji
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
                                text = categories.name,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            categories.comment?.let {
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
                                text = categories.sumString,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = categories.percentageString,
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
}

