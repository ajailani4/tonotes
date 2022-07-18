package com.tonotes.account_ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun FullSizeProgressBar() {
    Dialog(onDismissRequest = {}) {
        Surface {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
        }
    }
}