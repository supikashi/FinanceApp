package com.spksh.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spksh.ui.theme.red

@Composable
fun NoInternetBanner(isVisible: Boolean) {
    if (!isVisible) return
    Text(
        text = "Нет интернета",
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(red)
            .padding(16.dp),
    )
}