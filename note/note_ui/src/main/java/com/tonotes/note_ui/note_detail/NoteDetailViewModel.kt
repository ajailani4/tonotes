package com.tonotes.note_ui.note_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.util.Constants.NavArgument.NOTE_ID
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.DeleteNoteUseCase
import com.tonotes.note_domain.use_case.GetNoteDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    val noteId = savedStateHandle.get<Int>(NOTE_ID)!!

    var noteDetailState by mutableStateOf<UIState<Note>>(UIState.Idle)
        private set

    var menuVisibility by mutableStateOf(false)
        private set

    var deleteDialogVis by mutableStateOf(false)
        private set

    init {
        onEvent(NoteDetailEvent.GetNoteDetail)
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.GetNoteDetail -> getNoteDetail()

            is NoteDetailEvent.DeleteNote -> deleteNote()

            is NoteDetailEvent.OnMenuVisibilityChanged -> menuVisibility = event.menuVisibility

            is NoteDetailEvent.OnDeleteDialogVisChanged -> deleteDialogVis = event.deleteDialogVis
        }
    }

    private fun getNoteDetail() {
        viewModelScope.launch {
            val resource = getNoteDetailUseCase(noteId)

            resource.collect {
                noteDetailState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }
}