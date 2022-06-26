package com.tonotes.note_ui.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.InsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {
    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.OnTitleChanged -> title = event.title

            is AddEditNoteEvent.OnDescriptionChanged -> description = event.description

            is AddEditNoteEvent.InsertNote -> insertNote()
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
}