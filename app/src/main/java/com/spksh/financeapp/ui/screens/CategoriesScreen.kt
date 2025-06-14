package com.spksh.financeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spksh.financeapp.R
import com.spksh.financeapp.ui.components.ListItem
import com.spksh.financeapp.ui.components.TopBar
import com.spksh.financeapp.ui.models.CategoryUIModel

@Composable
fun CategoriesScreen(
    categoriesList: List<CategoryUIModel>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            headline = stringResource(R.string.my_categories),
        )
        TextField(
            value = "",
            onValueChange = {},
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
            items(categoriesList) { category ->
                ListItem(
                    leadIcon = category.emoji,
                    showTrailIcon = false,
                    minHeight = 72.dp,
                    isClickable = true
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
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
                    }
                }
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
fun CategoriesScreenPreview() {
    CategoriesScreen(
        categoriesList = CategoriesData.categoriesList
    )
}

object CategoriesData {
    val categoriesList: List<CategoryUIModel> = listOf(
        CategoryUIModel(
            id = 1,
            name = "Аренда квартиры",
            emoji = "🏠",
        ),
        CategoryUIModel(
            id = 2,
            name = "Одежда",
            emoji = "👗",
        ),
        CategoryUIModel(
            id = 3,
            name = "На собачку",
            emoji = "🐶",
            description = "Джек"
        ),
        CategoryUIModel(
            id = 4,
            name = "На собачку",
            emoji = "🐶",
            description = "Энни"
        ), CategoryUIModel(
            id = 5,
            name = "Ремонт квартиры",
            emoji = "РК",
        ),
        CategoryUIModel(
            id = 6,
            name = "Продукты",
            emoji = "🍭",
        ),
        CategoryUIModel(
            id = 7,
            name = "Спортзал",
            emoji = "🏋",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
    )
}