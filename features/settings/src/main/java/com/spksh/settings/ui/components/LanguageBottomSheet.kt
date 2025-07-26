package com.spksh.settings.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.spksh.financeapp.ui.R
import com.spksh.ui.components.ListItem
import com.spksh.ui.theme.red
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    onDismissRequest: () -> Unit = {},
    onChoose: (String) -> Unit = {},
    currentLanguage: String = ""
) {
    val dataList = listOf(
        "system" to stringResource(com.spksh.financeapp.settings.R.string.use_system),
        "en" to stringResource(com.spksh.financeapp.settings.R.string.english),
        "ru" to stringResource(com.spksh.financeapp.settings.R.string.russian)
    )
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
    ) {
        Column {
            dataList.forEach { data ->
                ListItem(
                    onClick = {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onChoose(data.first)
                                    onDismissRequest()
                                }
                            }
                    },
                    isClickable = true,
                    showTrailIcon = false,
                    containerColor = Color.Transparent
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (currentLanguage == data.first) {
                            Icon(painterResource(R.drawable.okay), null)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(data.second)
                    }
                }
                HorizontalDivider()
            }
        }
    }
}
