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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    addEditNoteViewModel: AddEditNoteViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = addEditNoteViewModel::onEvent
    val title = addEditNoteViewModel.title
    val description = addEditNoteViewModel.description

    var isTitleFocused by remember { mutableStateOf(false) }
    var isDescriptionFocused by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

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
                    onValueChange = { onEvent(AddEditNoteEvent.OnTitleChanged(it)) },
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
                    onValueChange = { onEvent(AddEditNoteEvent.OnDescriptionChanged(it)) },
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
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            onEvent(AddEditNoteEvent.InsertNote)
                            onNavigateUp()
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    context.resources.getString(R.string.please_fill_completely)
                                )
                            }
                        }
                    }
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