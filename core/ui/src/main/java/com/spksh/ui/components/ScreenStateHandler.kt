package com.spksh.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.spksh.ui.state.UiState

@Composable
fun <T>ScreenStateHandler(
    state: UiState<T>,
    content: @Composable (UiState.Success<T>) -> Unit,
    onRetryClick: () -> Unit = {}
) {
    when(state) {
        is UiState.Error -> {
            ErrorScreen(
                onRetryClick = onRetryClick
            )
        }
        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
        is UiState.Success<T> -> {
            content(state)
        }
    }
}