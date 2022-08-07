package com.tonotes.note_ui.add_edit_note

import androidx.compose.ui.text.input.TextFieldValue
import java.util.*

sealed class AddEditNoteEvent {
    object Idle : AddEditNoteEvent()
    object GetNoteDetail : AddEditNoteEvent()
    object InsertNote : AddEditNoteEvent()
    object EditNote : AddEditNoteEvent()
    data class OnTitleChanged(val title: String) : AddEditNoteEvent()
    data class OnDescriptionChanged(val description: TextFieldValue) : AddEditNoteEvent()
    data class SetDate(val date: Date) : AddEditNoteEvent()
}
