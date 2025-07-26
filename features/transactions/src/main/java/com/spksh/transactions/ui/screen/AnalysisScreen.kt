package com.spksh.transactions.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.spksh.graph.model.TransactionPlotModel
import com.spksh.graph.state.TransactionsAnalysisState
import com.spksh.graph.ui.TransactionsAnalysisPlot
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
                headline = stringResource(com.spksh.financeapp.transactions.R.string.analysis),
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
        leadingText = stringResource(com.spksh.financeapp.transactions.R.string.start_date),
        dateText = state.data.startDateString,
        onDateSelected = onStartDateSelected,
        backgroundColor = MaterialTheme.colorScheme.background,
        buttonColor = MaterialTheme.colorScheme.primary
    )
    HorizontalDivider()
    DatePickListItem(
        leadingText = stringResource(com.spksh.financeapp.transactions.R.string.end_date),
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
                text = stringResource(com.spksh.financeapp.transactions.R.string.sum),
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
                text = stringResource(com.spksh.financeapp.transactions.R.string.no_transactions),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                ) {
                    TransactionsAnalysisPlot(
                        state = TransactionsAnalysisState(data = state.data.categories.map {it.toPlotModel()}),
                        modifier = Modifier.height(140.dp)
                    )
                }
                HorizontalDivider()
            }
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

