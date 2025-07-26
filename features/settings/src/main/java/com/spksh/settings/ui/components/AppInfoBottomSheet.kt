package com.spksh.settings.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoBottomSheet(
    onDismissRequest: () -> Unit = {},
    version: String
) {

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(com.spksh.financeapp.settings.R.string.about_app),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "version: $version"
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "last update: n/a"
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}