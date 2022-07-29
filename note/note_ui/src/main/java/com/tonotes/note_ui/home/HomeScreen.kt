package com.tonotes.note_ui.home

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonotes.core_ui.R
import com.tonotes.core_ui.UIState
import com.tonotes.core_ui.component.CustomAlertDialog
import com.tonotes.note_ui.home.component.BackupTypeItem
import com.tonotes.note_ui.home.component.NoteCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToNoteDetail: (id: Int) -> Unit,
    onNavigateToAddEditNote: (id: Int) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val onEvent = homeViewModel::onEvent
    val notesState = homeViewModel.notesState
    val searchQuery = homeViewModel.searchQuery
    val isLoggedIn = homeViewModel.isLoggedIn
    val loginAlertDialogVis = homeViewModel.loginAlertDialogVis
    val backUpNotesDialogVis = homeViewModel.backUpNotesDialogVis
    val selectedBackupType = homeViewModel.selectedBackupType
    val syncIconRotationTargetValue = homeViewModel.syncIconRotationTargetValue

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val infiniteTransition = rememberInfiniteTransition()
    val syncIconAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = syncIconRotationTargetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        )
    )

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    
    val backupTypes = listOf(
        stringResource(id = R.string.manually),
        stringResource(id = R.string.daily),
        stringResource(id = R.string.weekly)
    )

    (LocalContext.current as Activity).window
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(HomeEvent.SyncNotes)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.graphicsLayer { rotationZ = syncIconAngle },
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sync notes icon"
                        )
                    }
                    IconButton(
                        onClick = {
                            onEvent(HomeEvent.OnLoginAlertDialogVisChanged(true))
                            onEvent(HomeEvent.OnBackUpNotesDialogVisChanged(true))
                            onEvent(HomeEvent.GetAccessToken)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CloudUpload,
                            contentDescription = "Back up notes icon"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    onNavigateToAddEditNote(0)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add icon"
                )
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 10.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                item {
                    SearchTextField(
                        searchQuery = searchQuery,
                        onSearchQueryChanged = {
                            onEvent(HomeEvent.OnSearchQueryChanged(it))
                            onEvent(HomeEvent.GetNotes)
                        },
                        focusManager = focusManager,
                        keyboardController = keyboardController
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }

                // Observe notes state
                when (notesState) {
                    is UIState.Success -> {
                        val notes = notesState.data

                        if (notes != null) {
                            if (notes.isNotEmpty()) {
                                items(notes) { note ->
                                    NoteCard(
                                        note = note,
                                        onClick = {
                                            onNavigateToNoteDetail(note.id!!)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            } else {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(top = 160.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.img_empty_notes),
                                            contentDescription = "Empty notes illustration"
                                        )
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Text(
                                            text = stringResource(id = R.string.no_notes),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is UIState.Fail -> {
                        coroutineScope.launch {
                            notesState.message?.let { snackbarHostState.showSnackbar(it) }
                        }
                    }

                    is UIState.Error -> {
                        coroutineScope.launch {
                            notesState.message?.let { snackbarHostState.showSnackbar(it) }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    // Observe dialog visibility state
    when {
        !isLoggedIn && loginAlertDialogVis -> {
            CustomAlertDialog(
                onVisibilityChanged = {
                    onEvent(HomeEvent.OnLoginAlertDialogVisChanged(false))
                },
                title = stringResource(id = R.string.need_to_login),
                content = {
                    Text(text = stringResource(id = R.string.need_to_have_an_account))
                },
                confirmText = stringResource(id = R.string.login),
                onConfirmed = {
                    onEvent(HomeEvent.OnLoginAlertDialogVisChanged(false))
                    onNavigateToLogin()
                },
                dismissText = stringResource(id = R.string.cancel),
                onDismissed = {
                    onEvent(HomeEvent.OnLoginAlertDialogVisChanged(false))
                }
            )
        }

        isLoggedIn && backUpNotesDialogVis -> {
            CustomAlertDialog(
                onVisibilityChanged = {
                    onEvent(HomeEvent.OnBackUpNotesDialogVisChanged(false))
                    onEvent(HomeEvent.GetSelectedBackupType)
                },
                title = stringResource(id = R.string.back_up_notes),
                content = {
                    Column {
                        backupTypes.forEachIndexed { index, backupType ->
                            BackupTypeItem(
                                backupType = backupType,
                                isSelected = index == selectedBackupType,
                                onClick = {
                                    onEvent(HomeEvent.OnBackupTypeSelected(index))
                                }
                            )
                        }
                    }
                },
                confirmText = stringResource(
                    id = if (selectedBackupType == 0) {
                        R.string.back_up
                    } else {
                        R.string.save
                    }
                ),
                onConfirmed = {
                    onEvent(HomeEvent.OnBackUpNotesDialogVisChanged(false))
                    onEvent(HomeEvent.BackUpNotes)
                },
                dismissText = stringResource(id = R.string.cancel),
                onDismissed = {
                    onEvent(HomeEvent.OnBackUpNotesDialogVisChanged(false))
                    onEvent(HomeEvent.GetSelectedBackupType)
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
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
        value = searchQuery,
        onValueChange = {
            onSearchQueryChanged(it)
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
            keyboardController?.hide()
        }),
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

                if (searchQuery.isEmpty() && !isFocused) {
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