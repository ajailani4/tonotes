package com.tonotes.note_ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.domain.use_case.GetAccessTokenUseCase
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.GetNotesUseCase
import com.tonotes.note_domain.use_case.GetSelectedBackupTypeUseCase
import com.tonotes.note_domain.use_case.SaveSelectedBackupTypeUseCase
import com.tonotes.note_domain.use_case.SyncNotesUseCase
import com.tonotes.note_domain.use_case.UploadNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val uploadNotesUseCase: UploadNotesUseCase,
    private val saveSelectedBackupTypeUseCase: SaveSelectedBackupTypeUseCase,
    private val getSelectedBackupTypeUseCase: GetSelectedBackupTypeUseCase,
    private val syncNotesUseCase: SyncNotesUseCase
) : ViewModel() {
    var notesState by mutableStateOf<UIState<List<Note>>>(UIState.Idle)
        private set

    var syncNotesState by mutableStateOf<UIState<String>>(UIState.Idle)
        private set

    var searchQuery by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    var loginAlertDialogVis by mutableStateOf(false)
        private set

    var backUpNotesDialogVis by mutableStateOf(false)
        private set

    var selectedBackupType by mutableStateOf(0)
        private set

    var syncIconRotationTargetValue by mutableStateOf(0f)
        private set

    init {
        onEvent(HomeEvent.GetNotes)
        onEvent(HomeEvent.GetAccessToken)
        onEvent(HomeEvent.GetSelectedBackupType)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Idle -> idle()

            is HomeEvent.GetNotes -> getNotes()

            is HomeEvent.GetAccessToken -> getAccessToken()

            is HomeEvent.BackUpNotes -> {
                backUpNotes()
                saveSelectedBackupType()
            }

            is HomeEvent.GetSelectedBackupType -> getSelectedBackupType()

            is HomeEvent.SyncNotes -> syncNotes()

            is HomeEvent.OnSearchQueryChanged -> searchQuery = event.searchQuery

            is HomeEvent.OnLoginAlertDialogVisChanged -> loginAlertDialogVis =
                event.loginAlertDialogVis

            is HomeEvent.OnBackUpNotesDialogVisChanged -> backUpNotesDialogVis =
                event.backUpNotesDialogVis

            is HomeEvent.OnBackupTypeSelected -> selectedBackupType = event.selectedBackupTypes
        }
    }

    private fun idle() {
        syncNotesState = UIState.Idle
    }

    private fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase(searchQuery).catch {
                notesState = UIState.Error(it.localizedMessage)
            }.collect {
                notesState = UIState.Success(it)
            }
        }
    }

    private fun getAccessToken() {
        viewModelScope.launch {
            getAccessTokenUseCase().collect {
                isLoggedIn = it.isNotEmpty()
            }
        }
    }

    private fun backUpNotes() {
        uploadNotesUseCase(selectedBackupType)
    }

    private fun saveSelectedBackupType() {
        viewModelScope.launch {
            saveSelectedBackupTypeUseCase(selectedBackupType)
        }
    }

    private fun getSelectedBackupType() {
        viewModelScope.launch {
            selectedBackupType = getSelectedBackupTypeUseCase().first()
        }
    }

    private fun syncNotes() {
        syncIconRotationTargetValue = -360f

        viewModelScope.launch {
            syncNotesUseCase().catch {
                syncIconRotationTargetValue = 0f
                syncNotesState = UIState.Error(it.localizedMessage)
            }.collect {
                syncNotesState = when (it) {
                    is Resource.Success -> {
                        syncIconRotationTargetValue = 0f
                        UIState.Success(it.data)
                    }

                    is Resource.Error -> {
                        syncIconRotationTargetValue = 0f
                        UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}