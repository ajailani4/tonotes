package com.tonotes.note_ui.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupTypeItem(
    backupType: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 9.dp,
                horizontal = 4.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                modifier = Modifier.size(20.dp),
                selected = isSelected,
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = backupType)
        }
    }
}