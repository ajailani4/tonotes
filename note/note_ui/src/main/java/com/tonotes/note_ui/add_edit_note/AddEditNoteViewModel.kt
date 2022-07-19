package com.tonotes.note_ui.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.util.Constants.NavArgument.NOTE_ID
import com.tonotes.core.util.Resource
import com.tonotes.core.util.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.EditNoteUseCase
import com.tonotes.note_domain.use_case.GetNoteDetailUseCase
import com.tonotes.note_domain.use_case.InsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteDetailUseCase: GetNoteDetailUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase
) : ViewModel() {
    val noteId = savedStateHandle.get<Int>(NOTE_ID)!!

    var noteDetailState by mutableStateOf<UIState<Note>>(UIState.Idle)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private var date = Date()

    init {
        if (noteId != 0) onEvent(AddEditNoteEvent.GetNoteDetail)
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.Idle -> idle()

            is AddEditNoteEvent.GetNoteDetail -> getNoteDetail()

            is AddEditNoteEvent.InsertNote -> insertNote()

            is AddEditNoteEvent.EditNote -> editNote()

            is AddEditNoteEvent.OnTitleChanged -> title = event.title

            is AddEditNoteEvent.OnDescriptionChanged -> description = event.description

            is AddEditNoteEvent.SetDate -> date = event.date
        }
    }

    private fun idle() {
        noteDetailState = UIState.Idle
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

    private fun insertNote() {
        viewModelScope.launch {
            insertNoteUseCase(
                title = title,
                description = description
            )
        }
    }

    private fun editNote() {
        viewModelScope.launch {
            editNoteUseCase(
                id = noteId,
                title = title,
                description = description,
                date = date
            )
        }
    }
}