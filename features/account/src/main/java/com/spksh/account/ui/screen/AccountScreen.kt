package com.spksh.account.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.account.ui.navigation.AccountUpdate
import com.spksh.account.ui.state.AccountScreenState
import com.spksh.account.ui.view_model.AccountViewModel
import com.spksh.financeapp.ui.R
import com.spksh.ui.state.UiState
import com.spksh.ui.components.AddButton
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.ScreenStateHandler
import com.spksh.ui.components.TopBar

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.syncAccount()
    }
    AccountScreenImpl(
        state = state,
        onAccountClick = {
            navController.navigate(AccountUpdate(it)) {
                launchSingleTop = true
                restoreState = true
            }
        },
        onRetryClick = {viewModel.fetchAccount()}
    )
}

@Composable
private fun AccountScreenImpl(
    state: UiState<AccountScreenState>,
    onAccountClick: (Long) -> Unit = {},
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
            ScreenStateHandler(
                state = state,
                content = {
                    AccountScreenSuccess(
                        state = it,
                        onAccountClick = onAccountClick
                    )
                },
                onRetryClick = onRetryClick
            )
        }
        AddButton(
            onCLick = {}
        )
    }
}

@Composable
private fun AccountScreenSuccess(
    state: UiState.Success<AccountScreenState>,
    onAccountClick: (Long) -> Unit = {},
) {
    LazyColumn {
        items(state.data.accounts) { account ->
            ListItem(
                leadIcon = "💰",
                minHeight = 56.dp,
                containerColor = MaterialTheme.colorScheme.secondary,
                emojiContainerColor = MaterialTheme.colorScheme.background,
                isClickable = true,
                onClick = {
                    onAccountClick(account.id)
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = account.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
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
                isClickable = true,
                onClick = {
                    onAccountClick(account.id)
                }
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

//@Preview
//@Composable
//fun AccountScreenPreview() {
//    AccountScreenImpl(
//        state = UiState.Success(data = AccountScreenState(accounts = MockData.accountsList))
//    )
//}


