package com.tonotes.core_ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tonotes.core_ui.R

@Composable
fun CustomAlertDialog(
    onVisibilityChanged: () -> Unit,
    title: String,
    content: @Composable () -> Unit,
    confirmText: String,
    onConfirmed: () -> Unit,
    dismissText: String,
    onDismissed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onVisibilityChanged,
        title = {
            Text(text = title)
        },
        text = content,
        confirmButton = {
            TextButton(onClick = onConfirmed) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissed) {
                Text(text = dismissText)
            }
        }
    )
}