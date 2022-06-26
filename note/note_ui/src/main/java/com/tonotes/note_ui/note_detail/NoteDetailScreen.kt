package com.tonotes.note_ui.note_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
    val noteDetailState = noteDetailViewModel.noteDetailState

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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More icon"
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
                    .padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
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
                            Spacer(modifier = Modifier.height(7.dp))
                            Text(
                                text = note.date.convertToString(),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = note.description,
                                color = MaterialTheme.colorScheme.onBackground
                            )
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