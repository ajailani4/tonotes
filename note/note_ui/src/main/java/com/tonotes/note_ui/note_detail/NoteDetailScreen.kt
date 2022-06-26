package com.tonotes.note_ui.note_detail

import com.tonotes.core.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonotes.core.UIState
import com.tonotes.core.util.convertToString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = noteDetailViewModel::onEvent
    val noteDetailState = noteDetailViewModel.noteDetailState
    val menuVisibility = noteDetailViewModel.menuVisibility

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
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(NoteDetailEvent.OnMenuVisibilityChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More icon"
                        )
                    }
                    DropdownMenu(
                        expanded = menuVisibility,
                        onDismissRequest = {
                            onEvent(NoteDetailEvent.OnMenuVisibilityChanged(false))
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.edit),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            onClick = {
                                onEvent(NoteDetailEvent.OnMenuVisibilityChanged(false))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit icon"
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.delete),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            onClick = {
                                onEvent(NoteDetailEvent.OnMenuVisibilityChanged(false))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete icon"
                                )
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp)
                    .padding(horizontal = 20.dp)
            ) {
                when (noteDetailState) {
                    is UIState.Success -> {
                        val note = noteDetailState.data

                        if (note != null) {
                            Text(
                                text = note.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = note.date.convertToString(),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            Text(
                                text = note.description,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    is UIState.Fail -> {
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                noteDetailState.message?.let { snackbarHostState.showSnackbar(it) }
                            }
                        }
                    }

                    is UIState.Error -> {
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                noteDetailState.message?.let { snackbarHostState.showSnackbar(it) }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}