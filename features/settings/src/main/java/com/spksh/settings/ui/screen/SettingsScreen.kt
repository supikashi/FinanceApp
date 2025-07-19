package com.spksh.settings.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spksh.financeapp.ui.R
import com.spksh.settings.ui.view_model.SettingsViewModel
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.TopBar

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val settingsList = listOf(
        "Основной цвет",
        "Звуки",
        "Хаптики",
        "Код пароль",
        "Синхронизация",
        "Язык",
        "О программе"
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            headline = "Настройки",
        )
        ListItem(
            showTrailIcon = false,
            minHeight = 56.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Тёмная тема",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = false,
                    onCheckedChange = {  },
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = MaterialTheme.colorScheme.outline,
                        uncheckedBorderColor = MaterialTheme.colorScheme.outline,
                        checkedThumbColor = MaterialTheme.colorScheme.outline,
                        uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                    )
                )
            }
        }
        HorizontalDivider()
        settingsList.forEach {
            ListItem(
                showTrailIcon = false,
                minHeight = 56.dp,
                isClickable = true
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (it == "Синхронизация") {
                        Text(
                            text = "Синхронизировано",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(
                            onClick = {
                                viewModel.sync()
                            }
                        ) {
                            Text(
                                text = state.value ?: "Not synced",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    } else {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            painter =  painterResource(R.drawable.arrow_right),
                            contentDescription = stringResource(R.string.more),
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

//@Preview
//@Composable
//fun SettingsScreenPreview() {
//    SettingsScreen()
//}