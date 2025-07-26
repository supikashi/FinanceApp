package com.spksh.settings.ui.screen

import android.app.Activity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spksh.financeapp.ui.R
import com.spksh.settings.ui.components.AppInfoBottomSheet
import com.spksh.settings.ui.components.LanguageBottomSheet
import com.spksh.settings.ui.components.MainColorBottomSheet
import com.spksh.settings.ui.state.SettingsScreenState
import com.spksh.settings.ui.view_model.SettingsViewModel
import com.spksh.ui.components.ListItem
import com.spksh.ui.components.TopBar
import com.spksh.ui.state.UiState
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    version: String
) {
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    (state.value as? UiState.Success<SettingsScreenState>)?.let { state ->
        var showLanguageSheet by remember { mutableStateOf(false) }
        var showInfoSheet by remember { mutableStateOf(false) }
        var showColorSheet by remember { mutableStateOf(false) }
        var isDarkTheme by remember { mutableStateOf(state.data.isDarkTheme) }
        val settingsList = listOf(
            stringResource(com.spksh.financeapp.settings.R.string.main_color) to {showColorSheet = true},
            stringResource(com.spksh.financeapp.settings.R.string.haptics) to {},
            stringResource(com.spksh.financeapp.settings.R.string.code) to {},
            stringResource(com.spksh.financeapp.settings.R.string.synchronization) to {},
            stringResource(com.spksh.financeapp.settings.R.string.language) to {showLanguageSheet = true},
            stringResource(com.spksh.financeapp.settings.R.string.about) to {showInfoSheet = true}
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                headline = stringResource(com.spksh.financeapp.settings.R.string.settings),
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
                        text = stringResource(com.spksh.financeapp.settings.R.string.dark_theme),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            isDarkTheme = it
                            scope.launch {
                                viewModel.saveTheme(it)
                                (context as? Activity)?.recreate()
                            }
                        },
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
                    isClickable = true,
                    onClick = it.second
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (it.first == stringResource(com.spksh.financeapp.settings.R.string.synchronization)) {
                            Text(
                                text = stringResource(com.spksh.financeapp.settings.R.string.synced),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Button(
                                onClick = {
                                    viewModel.sync()
                                }
                            ) {
                                Text(
                                    text = state.data.syncTime ?: stringResource(com.spksh.financeapp.settings.R.string.not_synced),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        } else {
                            Text(
                                text = it.first,
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
        if (showLanguageSheet) {
            LanguageBottomSheet(
                onDismissRequest = { showLanguageSheet = false },
                onChoose = {
                    scope.launch {
                        viewModel.saveLanguage(it)
                        (context as? Activity)?.recreate()
                    }
                },
                currentLanguage = state.data.currentLanguage
            )
        }
        if (showInfoSheet) {
            AppInfoBottomSheet(
                onDismissRequest = {showInfoSheet = false},
                version = version
            )
        }
        if (showColorSheet) {
            MainColorBottomSheet(
                onDismissRequest = {showColorSheet = false},
                onChoose = {
                    scope.launch {
                        viewModel.saveColor(it)
                    }
                },
                current = state.data.color
            )
        }
    }
}