package com.tonotes.note_ui.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.util.Constants.NavArgument.NOTE_ID
import com.tonotes.core_ui.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.EditNoteUseCase
import com.tonotes.note_domain.use_case.GetNoteDetailUseCase
import com.tonotes.note_domain.use_case.InsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
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

    // TextFieldValue is used because we want to get cursor position in description text field
    var description by mutableStateOf(TextFieldValue(""))
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
            getNoteDetailUseCase(noteId).catch {
                noteDetailState = UIState.Error(it.localizedMessage)
            }.collect {
                noteDetailState = UIState.Success(it)
            }
        }
    }

    private fun insertNote() {
        viewModelScope.launch {
            insertNoteUseCase(
                title = title,
                description = description.text
            )
        }
    }

    private fun editNote() {
        viewModelScope.launch {
            editNoteUseCase(
                id = noteId,
                title = title,
                description = description.text,
                date = date
            )
        }
    }
}