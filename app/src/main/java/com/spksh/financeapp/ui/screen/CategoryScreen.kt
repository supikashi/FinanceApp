package com.spksh.financeapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spksh.financeapp.MockData
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.ScreenStateHandler
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.model.CategoryUIModel
import com.spksh.financeapp.ui.state.AccountScreenState
import com.spksh.financeapp.ui.state.CategoryScreenState
import com.spksh.financeapp.ui.state.UiState
import com.spksh.financeapp.ui.viewModel.AccountViewModel
import com.spksh.financeapp.ui.viewModel.CategoryViewModel

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    CategoryScreenImpl(
        state = state,
        onRetryClick = { viewModel.fetchData() },
        onValueChange = { viewModel.filterCategories(it) }
    )
}

@Composable
private fun CategoryScreenImpl(
    state: UiState<CategoryScreenState>,
    onRetryClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            headline = stringResource(R.string.my_categories),
        )
        ScreenStateHandler(
            state = state,
            content = {
                CategoryScreenSuccess(
                    state = it,
                    onValueChange = onValueChange
                )
            },
            onRetryClick = onRetryClick
        )
    }
}

@Composable
private fun CategoryScreenSuccess(
    state: UiState.Success<CategoryScreenState>,
    onValueChange: (String) -> Unit
) {
    var filterName by remember { mutableStateOf("") }
    TextField(
        value = filterName,
        onValueChange = {
            filterName = it
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.find_category),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.loupe),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.outline
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onBackground
        )
    )
    HorizontalDivider()
    LazyColumn {
        items(state.data.filteredCategories) { category ->
            ListItem(
                leadIcon = category.emoji,
                showTrailIcon = false,
                minHeight = 72.dp,
                isClickable = true
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

@Preview
@Composable
fun CategoriesScreenPreview() {
    CategoryScreenImpl(
        state = UiState.Success(data = CategoryScreenState(MockData.categoriesList, emptyList()))
    )
}
