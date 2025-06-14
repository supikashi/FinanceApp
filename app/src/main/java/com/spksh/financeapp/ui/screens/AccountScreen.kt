package com.spksh.financeapp.ui.screens

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
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.models.AccountUIModel

@Composable
fun AccountScreen(
    accountsList: List<AccountUIModel>
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
            LazyColumn {
                items(accountsList) { account ->
                    ListItem(
                        leadIcon = account.emoji,
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

@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        accountsList = AccountData.accountsList
    )
}

object AccountData {
    val accountsList: List<AccountUIModel> = listOf(
        AccountUIModel(
            id = 0,
            name = "Основной счет",
            emoji = "💰",
            balance = "-670 000 ₽",
            currency = "₽",
        )
    )
}

