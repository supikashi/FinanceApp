package com.spksh.settings.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spksh.financeapp.ui.R
import com.spksh.ui.components.ListItem
import com.spksh.ui.theme.mainSchemesList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainColorBottomSheet(
    onDismissRequest: () -> Unit = {},
    onChoose: (Int) -> Unit = {},
    current: Int = 0
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
    ) {
        Column {
            mainSchemesList.forEachIndexed { i, data ->
                ListItem(
                    onClick = {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onChoose(i)
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
                        if (current == i) {
                            Icon(painterResource(R.drawable.okay), null)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .background(color = data.primaryLight, shape = CircleShape)
                                .weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .background(color = data.primaryDark, shape = CircleShape)
                                .weight(1f)
                        )
                    }
                }
                HorizontalDivider()
            }
        }
    }
}