package com.spksh.financeapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.ScreenStateHandler
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.model.AccountUpdateUiModel
import com.spksh.financeapp.ui.state.AccountUpdateScreenState
import com.spksh.financeapp.ui.state.UiState
import com.spksh.financeapp.ui.theme.red
import com.spksh.financeapp.ui.utils.formatCurrency
import com.spksh.financeapp.ui.viewModel.AccountUpdateViewModel
import kotlinx.coroutines.launch

@Composable
fun AccountUpdateScreen(
    viewModel: AccountUpdateViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    AccountUpdateScreenImpl(
        state = state,
        onBackClick = { navController.popBackStack() },
        onSaveClick = {
            viewModel.updateAccount(
                it,
                { navController.popBackStack() },
                { Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_SHORT).show() }
            )
        },
        onLocalSaveClick = { viewModel.localUpdateAccount(it) },
        onRetryClick = { viewModel.retryLoad() }
    )
}

@Composable
private fun AccountUpdateScreenImpl(
    state: UiState<AccountUpdateScreenState>,
    onBackClick: () -> Unit = {},
    onSaveClick: (AccountUpdateUiModel) -> Unit = {},
    onLocalSaveClick: (AccountUpdateUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = stringResource(R.string.my_account),
                leftIcon = R.drawable.cancel,
                rightIcon = R.drawable.okay,
                leftIconOnClick = onBackClick,
                rightIconOnClick = {
                    if (state is UiState.Success<AccountUpdateScreenState>) {
                        onSaveClick(state.data.accountUpdateData)
                    }
                }
            )
            ScreenStateHandler(
                state = state,
                content = {
                    AccountUpdateScreenSuccess(
                        state = it,
                        onAccountChange = onLocalSaveClick
                    )
                },
                onRetryClick = onRetryClick
            )
        }
    }
}

@Composable
private fun AccountUpdateScreenSuccess(
    state: UiState.Success<AccountUpdateScreenState>,
    onAccountChange: (AccountUpdateUiModel) -> Unit = {},
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    Column {
        CustomTextField(
            text = state.data.accountUpdateData.name,
            onTextChange = { onAccountChange(state.data.accountUpdateData.copy(name = it)) },
            label = "Название счета"
        )
        HorizontalDivider()
        CustomTextField(
            text = state.data.accountUpdateData.balance,
            onTextChange = { onAccountChange(state.data.accountUpdateData.copy(balance = it)) },
            label = "Баланс"
        )

        HorizontalDivider()
        ListItem(
            minHeight = 56.dp,
            containerColor = MaterialTheme.colorScheme.secondary,
            isClickable = true,
            onClick = {
                openBottomSheet = true
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
                    text = formatCurrency(state.data.accountUpdateData.currency),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
    if (openBottomSheet) {
        BottomSheet(
            onDismissRequest = { openBottomSheet = false },
            onChooseCurrency = { onAccountChange(state.data.accountUpdateData.copy(currency = it)) }
        )
    }
}

@Composable
private fun CustomTextField(
    text: String = "",
    onTextChange: (String) -> Unit = {},
    label: String = ""
) {
    TextField(
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        label = { Text(label) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit = {},
    onChooseCurrency: (String) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val dataList = listOf(
        CurrencyListItemData(
            name = "Российский рубль",
            icon = R.drawable.ruble,
            currency = "RUB"
        ),
        CurrencyListItemData(
            name = "Американский доллар",
            icon = R.drawable.dollar,
            currency = "USD"
        ),
        CurrencyListItemData(
            name = "Евро",
            icon = R.drawable.euro,
            currency = "EUR"
        )
    )
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
    ) {
        Column {
            dataList.forEach {
                CurrencyListItem(
                    onClick = {
                        onChooseCurrency(it.currency)
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onDismissRequest()
                                }
                            }
                    },
                    text = it.name,
                    icon = it.icon
                )
                HorizontalDivider()
            }
            CurrencyListItem(
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest()
                            }
                        }
                },
                text = "Отмена",
                icon = R.drawable.cancellation_item,
                containerColor = red,
                contentColor = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
fun CurrencyListItem(
    onClick: () -> Unit = {},
    text: String = "",
    icon: Int? = null,
    containerColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onBackground
) {
    ListItem(
        isClickable = true,
        onClick = onClick,
        showTrailIcon = false,
        containerColor = containerColor
    ) {
        Row {
            icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = contentColor
                )
                Spacer(Modifier.width(16.dp))
            }
            Text(
                text = text,
                color = contentColor
            )
        }
    }
}

data class CurrencyListItemData(
    val name: String = "",
    val icon: Int? = null,
    val currency: String = ""
)

@Preview
@Composable
fun AccountUpdateScreenPreview() {
    AccountUpdateScreenImpl(
        state = UiState.Success(data = AccountUpdateScreenState(accountUpdateData = AccountUpdateUiModel("Счет", "1000.0", "USD")))
    )
}