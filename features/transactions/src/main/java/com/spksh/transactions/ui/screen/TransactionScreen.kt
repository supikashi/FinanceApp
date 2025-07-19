package com.spksh.transactions.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.spksh.financeapp.ui.R
import com.spksh.transactions.ui.component.DatePickListItem
import com.spksh.transactions.ui.component.TimePickListItem
import com.spksh.transactions.ui.model.TransactionRequestUiModel
import com.spksh.transactions.ui.state.TransactionScreenState
import com.spksh.transactions.ui.view_model.transaction.TransactionViewModel
import com.spksh.ui.components.DatePickerWrap
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.ScreenStateHandler
import com.spksh.ui.components.TopBar
import com.spksh.ui.model.CategoryUIModel
import com.spksh.ui.state.UiState
import com.spksh.ui.theme.red
import kotlinx.coroutines.launch

@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel,
    navController: NavController,
    transactionId: Long?,
    isIncome: Boolean
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var showLoader by remember { mutableStateOf(false) }
    LaunchedEffect(transactionId) {
        viewModel.setViewModel(transactionId, isIncome)
    }
    TransactionScreenImpl(
        state = state,
        isIncome = isIncome,
        onBackClick = {navController.popBackStack()},
        onRetryClick = {viewModel.retryLoad()},
        onLocalSaveClick = {viewModel.localUpdateTransaction(it)},
        onSetDate = {viewModel.changeDate(it)},
        onSetTime = {viewModel.changeTime(it)},
        onSaveClick = {
            if (!showLoader) {
                showLoader = true
                viewModel.updateTransaction(
                    it,
                    { navController.popBackStack() },
                    {
                        showLoader = false
                        Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        },
        onDelete = {
            showLoader = true
            viewModel.deleteTransaction(
                { navController.popBackStack() },
                {
                    showLoader = false
                    Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show()
                }
            )
        },
        showLoader = showLoader
    )
}

@Composable
private fun TransactionScreenImpl(
    state: UiState<TransactionScreenState>,
    isIncome: Boolean,
    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onLocalSaveClick: (TransactionRequestUiModel) -> Unit = {},
    onSetDate: (Long?) -> Unit = {},
    onSetTime: (Int) -> Unit = {},
    onSaveClick: (TransactionRequestUiModel) -> Unit = {},
    onDelete: () -> Unit = {},
    showLoader: Boolean = false
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = if (isIncome) "Доход" else "Расход",
                rightIcon = R.drawable.okay,
                rightIconOnClick = {
                    (state as? UiState.Success)?.let {
                        onSaveClick(it.data.transaction)
                    }
                },
                rightIconContentDescription = null,
                leftIcon = R.drawable.cancel,
                leftIconOnClick = onBackClick,
                leftIconContentDescription = null,
            )
            ScreenStateHandler(
                state = state,
                content = {
                    TransactionScreenSuccess(
                        state = it,
                        isIncome = isIncome,
                        onLocalSaveClick = onLocalSaveClick,
                        onSetDate = onSetDate,
                        onSetTime = onSetTime,
                        onDelete = onDelete,
                        showLoader = showLoader
                    )
                },
                onRetryClick = onRetryClick,
            )
        }
    }
}

@Composable
private fun TransactionScreenSuccess(
    state: UiState.Success<TransactionScreenState>,
    isIncome: Boolean,
    onLocalSaveClick: (TransactionRequestUiModel) -> Unit = {},
    onSetDate: (Long?) -> Unit = {},
    onSetTime: (Int) -> Unit = {},
    onDelete: () -> Unit = {},
    showLoader: Boolean = false
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ListItem() {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Счет")
                    Text(state.data.accountList.firstOrNull { it.id == state.data.transaction.accountId }?.name ?: state.data.accountList.first().name)
                }
            }
            HorizontalDivider()
            ListItem(
                isClickable = true,
                onClick = {openBottomSheet = true}
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Статья")
                    Text(state.data.categoryList.firstOrNull { it.id == state.data.transaction.categoryId }?.name ?: state.data.categoryList.first().name)
                }
            }
            HorizontalDivider()
            CustomTextField(
                text = state.data.transaction.amount,
                onTextChange = { onLocalSaveClick(state.data.transaction.copy(amount = it)) },
                label = "Сумма",
            )
            HorizontalDivider()
            DatePickListItem(
                leadingText = "Дата",
                dateText = state.data.transaction.dateString,
                onDateSelected = onSetDate,
                height = 68,
                containerColor = Color.Transparent
            )
            HorizontalDivider()
            TimePickListItem(
                leadingText = "Время",
                timeText = state.data.transaction.timeString,
                onTimeSelected = onSetTime,
                height = 68,
                containerColor = Color.Transparent
            )
            HorizontalDivider()
            CustomTextField(
                text = state.data.transaction.comment,
                onTextChange = { onLocalSaveClick(state.data.transaction.copy(comment = it)) },
                label = "Комментарий"
            )
            HorizontalDivider()
            Button(
                onClick = {onDelete()},
                colors = ButtonDefaults.buttonColors(containerColor = red),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(if (isIncome) "Удалить доход" else "Удалить расход")
            }
        }
        if (showLoader) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            )
        }
    }
    if (openBottomSheet) {
        BottomSheet(
            onDismissRequest = { openBottomSheet = false },
            onChooseCategory = { onLocalSaveClick(state.data.transaction.copy(categoryId = it)) },
            categories = state.data.categoryList
        )
    }
    if (showDatePicker) {
        DatePickerWrap(
            onDateSelected = { dateState ->
                onSetDate(dateState)
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit = {},
    onChooseCategory: (Long) -> Unit = {},
    categories: List<CategoryUIModel> = emptyList()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
    ) {
        LazyColumn {
            items(categories) { category ->
                ListItem(
                    leadIcon = category.emoji,
                    showTrailIcon = false,
                    isClickable = true,
                    onClick = {
                        onChooseCategory(category.id)
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onDismissRequest()
                                }
                            }
                    },
                    containerColor = Color.Transparent
                ) {
                    Text(
                        text = category.name,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                HorizontalDivider()
            }

        }
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
        modifier = Modifier.fillMaxWidth().height(68.dp),
        label = { Text(label) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

//@Preview(showBackground = true)
//@Composable
//fun TransactionScreenPreview() {
////    TransactionScreenImpl(
////        state = UiState.Success(TransactionScreenState(data = "aboba"))
////    )
//    Text("df")
//}