package com.tonotes.note_ui.note_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonotes.core.util.Constants.TestTag
import com.tonotes.core.R
import com.tonotes.core.util.UIState
import com.tonotes.core.util.convertToString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToAddEditNote: (id: Int) -> Unit,
    onNavigateToHome: () -> Unit
) {
    val noteId = noteDetailViewModel.noteId
    val onEvent = noteDetailViewModel::onEvent
    val noteDetailState = noteDetailViewModel.noteDetailState
    val menuVisibility = noteDetailViewModel.menuVisibility
    val deleteDialogVis = noteDetailViewModel.deleteDialogVis

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
                            contentDescription = "More vertical icon"
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
                                Text(text = stringResource(id = R.string.edit))
                            },
                            onClick = {
                                onNavigateToAddEditNote(noteId)
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
                                Text(text = stringResource(id = R.string.delete))
                            },
                            onClick = {
                                onEvent(NoteDetailEvent.OnDeleteDialogVisChanged(true))
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
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Observe note detail state
            when (noteDetailState) {
                is UIState.Success -> {
                    val note = noteDetailState.data

                    if (note != null) {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 20.dp)
                                .padding(horizontal = 20.dp)
                        ) {
                            Text(
                                modifier = Modifier.testTag(TestTag.TITLE_TEXT),
                                text = note.title,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = note.date.convertToString("dd MMM yyyy"),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            Text(
                                modifier = Modifier.testTag(TestTag.DESCRIPTION_TEXT),
                                text = note.description
                            )
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

    if (deleteDialogVis) {
        AlertDialog(
            onDismissRequest = {
                onEvent(NoteDetailEvent.OnDeleteDialogVisChanged(false))
            },
            title = {
                Text(text = stringResource(id = R.string.delete_note))
            },
            text = {
                Text(text = stringResource(id = R.string.delete_note_confirm_msg))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(NoteDetailEvent.OnDeleteDialogVisChanged(false))
                        onEvent(NoteDetailEvent.DeleteNote)
                        onNavigateToHome()
                    }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(NoteDetailEvent.OnDeleteDialogVisChanged(false))
                    }
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
        )
    }
}