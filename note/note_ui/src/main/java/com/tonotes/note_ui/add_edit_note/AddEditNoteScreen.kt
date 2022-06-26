package com.tonotes.note_ui.add_edit_note

import com.tonotes.core.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    onNavigateUp: () -> Unit
) {
    val (title, setTitle) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    var isTitleFocused by remember { mutableStateOf(false) }
    var isDescriptionFocused by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 20.dp)
                    .padding(horizontal = 20.dp)
            ) {
                // Title
                BasicTextField(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .fillMaxWidth()
                        .onFocusChanged { isTitleFocused = it.isFocused },
                    value = title,
                    onValueChange = setTitle,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (title.isEmpty() && !isTitleFocused) {
                                Text(
                                    text = stringResource(id = R.string.title),
                                    color = MaterialTheme.colorScheme.outline,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(25.dp))
                
                // Description
                BasicTextField(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .fillMaxWidth()
                        .onFocusChanged { isDescriptionFocused = it.isFocused },
                    value = description,
                    onValueChange = setDescription,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (description.isEmpty() && !isDescriptionFocused) {
                                Text(
                                    text = stringResource(id = R.string.description),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            }
            Surface(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.background
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}