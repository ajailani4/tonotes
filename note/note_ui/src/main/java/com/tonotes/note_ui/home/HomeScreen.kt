package com.tonotes.note_ui.home

import com.tonotes.core.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tonotes.note_domain.model.Note
import com.tonotes.note_ui.home.component.NoteCard

@Composable
fun HomeScreen() {
    val (text, setText) = remember { mutableStateOf("") }
    val notes = listOf(
        Note(
            id = 1,
            title = "Lorem Ipsum",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi felis est, dictum consectetur enim vitae, venenatis lobortis augue.",
            date = "22 June 2022"
        ),
        Note(
            id = 2,
            title = "Lorem Ipsum",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi felis est, dictum consectetur enim vitae, venenatis lobortis augue.",
            date = "22 June 2022"
        )
    )

    LazyColumn(contentPadding = PaddingValues(20.dp)) {
        item {
            SearchTextField(text, setText)
            Spacer(modifier = Modifier.height(25.dp))
        }

        items(notes) { note ->
            NoteCard(
                note = note,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SearchTextField(
    text: String,
    onTextChanged: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = CircleShape
            )
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        value = text,
        onValueChange = onTextChanged,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = MaterialTheme.typography.bodyLarge,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = "Search icon"
                )
                Spacer(modifier = Modifier.width(12.dp))

                if (text.isEmpty() && !isFocused) {
                    Text(
                        text = stringResource(id = R.string.search_notes),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                innerTextField()
            }
        }
    )
}