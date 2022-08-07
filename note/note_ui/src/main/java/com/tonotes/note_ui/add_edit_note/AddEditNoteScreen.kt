package com.tonotes.note_ui.add_edit_note

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonotes.core.util.Constants.TestTag
import com.tonotes.core_ui.R
import com.tonotes.core_ui.UIState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    addEditNoteViewModel: AddEditNoteViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val noteId = addEditNoteViewModel.noteId
    val onEvent = addEditNoteViewModel::onEvent
    val noteDetailState = addEditNoteViewModel.noteDetailState
    val title = addEditNoteViewModel.title
    val description = addEditNoteViewModel.description

    var isTitleFocused by remember { mutableStateOf(false) }
    var isDescriptionFocused by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState(0)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    (LocalContext.current as Activity).window
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = if (noteId == 0) R.string.add_note else R.string.edit_note),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 80.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    // Title
                    BasicTextField(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .fillMaxWidth()
                            .onFocusChanged { isTitleFocused = it.isFocused }
                            .testTag(TestTag.TITLE_TEXT_FIELD),
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
                            .onFocusChanged { isDescriptionFocused = it.isFocused }
                            .testTag(TestTag.DESCRIPTION_TEXT_FIELD),
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
            }
            Surface(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.background
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 20.dp),
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            if (noteId == 0) {
                                onEvent(AddEditNoteEvent.InsertNote)
                            } else {
                                onEvent(AddEditNoteEvent.EditNote)
                            }

                            onNavigateToHome()
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

            // Scroll down automatically when user enter new line
            LaunchedEffect(scrollState.maxValue) {
                scrollState.scrollTo(scrollState.maxValue)
            }
        }
    }

    // Observe note detail state
    when (noteDetailState) {
        is UIState.Success -> {
            val note = noteDetailState.data

            if (note != null) {
                onEvent(AddEditNoteEvent.OnTitleChanged(note.title))
                onEvent(AddEditNoteEvent.OnDescriptionChanged(note.description))
                onEvent(AddEditNoteEvent.SetDate(note.date))
            }

            onEvent(AddEditNoteEvent.Idle)
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