package com.spksh.financeapp.ui.screens

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
import com.spksh.financeapp.ui.models.CategoryWithSumUIModel

@Composable
fun IncomeScreen(
    sumText: String = "",
    categoriesList: List<CategoryWithSumUIModel> = emptyList()
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = stringResource(R.string.income_today),
                rightIcon = R.drawable.update,
                rightIconOnClick = {},
                rightIconContentDescription = stringResource(R.string.show_history)
            )
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
                        text = sumText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            HorizontalDivider()
            LazyColumn {
                items(categoriesList) { category ->
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
fun IncomeScreenPreview() {
    IncomeScreen(
        sumText = IncomeData.sumText,
        categoriesList = IncomeData.categoriesList
    )
}

object IncomeData {
    val sumText = "600 000 ₽"
    val categoriesList: List<CategoryWithSumUIModel> = listOf(
        CategoryWithSumUIModel(
            id = 1,
            name = "Зарплата",
            sum = "500 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 2,
            name = "Подработка",
            sum = "100 000 ₽",
        )
    )
}