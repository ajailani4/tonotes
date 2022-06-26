package com.tonotes.note_ui.note_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.Constants.NavArgument.NOTE_ID
import com.tonotes.core.Resource
import com.tonotes.core.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.GetNoteDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteDetailUseCase: GetNoteDetailUseCase
) : ViewModel() {
    val noteId = savedStateHandle.get<Int>(NOTE_ID)!!

    var noteDetailState by mutableStateOf<UIState<Note>>(UIState.Idle)
        private set

    var menuVisibility by mutableStateOf(false)
        private set

    init {
        getNoteDetail()
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.OnMenuVisibilityChanged -> menuVisibility = event.menuVisibility
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
}