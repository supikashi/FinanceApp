package com.spksh.financeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.spksh.financeapp.MockData
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.ErrorScreen
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.state.AccountUiState
import com.spksh.financeapp.ui.viewModel.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    AccountScreenImpl(
        state = state,
        onRetryClick = {viewModel.fetchAccount()}
    )
}

@Composable
private fun AccountScreenImpl(
    state: AccountUiState,
    onRetryClick: () -> Unit = {}
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = stringResource(R.string.my_account),
                rightIcon = R.drawable.edit,
                rightIconOnClick = {},
                rightIconContentDescription = stringResource(R.string.edit)
            )
            when(state) {
                is AccountUiState.Success -> {
                    AccountScreenSuccess(state)
                }
                is AccountUiState.Loading -> {
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
                is AccountUiState.Error -> {
                    ErrorScreen(
                        onRetryClick = onRetryClick
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
private fun AccountScreenSuccess(
    state: AccountUiState.Success
) {
    LazyColumn {
        items(state.accounts) { account ->
            ListItem(
                leadIcon = "💰",
                minHeight = 56.dp,
                containerColor = MaterialTheme.colorScheme.secondary,
                emojiContainerColor = MaterialTheme.colorScheme.background,
                isClickable = true
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Баланс",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = account.balance,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            HorizontalDivider()
            ListItem(
                minHeight = 56.dp,
                containerColor = MaterialTheme.colorScheme.secondary,
                isClickable = true
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.currency),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = account.currency,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(Modifier.height(220.dp))
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreenImpl(
        state = AccountUiState.Success(accounts = MockData.accountsList)
    )
}


