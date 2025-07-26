package com.spksh.transactions.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.spksh.financeapp.transactions.R
import com.spksh.ui.components.DatePickerWrap
import com.spksh.ui.components.ListItem
import java.time.LocalTime

@Composable
fun DatePickListItem(
    leadingText: String = "",
    dateText: String = "",
    onDateSelected: (Long?) -> Unit = {},
    height: Int = 56,
    containerColor: Color = MaterialTheme.colorScheme.secondary
) {
    var showDatePicker by remember { mutableStateOf(false) }
    ListItem(
        showTrailIcon = false,
        minHeight = height.dp,
        containerColor = containerColor,
        isClickable = true,
        onClick = {showDatePicker = true}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = leadingText,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    if (showDatePicker) {
        DatePickerWrap(
            onDateSelected = { dateState ->
                onDateSelected(dateState)
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickListItem(
    leadingText: String = "",
    timeText: String = "",
    onTimeSelected: (Int) -> Unit = {},
    height: Int = 56,
    containerColor: Color = MaterialTheme.colorScheme.secondary
) {
    var showTimePicker by remember { mutableStateOf(false) }
    ListItem(
        showTrailIcon = false,
        minHeight = height.dp,
        containerColor = containerColor,
        isClickable = true,
        onClick = {showTimePicker = true}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = leadingText,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = timeText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    if (showTimePicker) {
        TimePickerDialog(
            onDismiss = {showTimePicker = false},
            onConfirm = {onTimeSelected(60 * it.hour + it.minute)}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState) -> Unit,
) {
    val currentTime = LocalTime.now()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute,
        is24Hour = true,
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(timePickerState)
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.cancel))
            }
        },
        text = { TimePicker(state = timePickerState) }
    )
}

