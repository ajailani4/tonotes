package com.tonotes.note_ui.add_edit_note

sealed class AddEditNoteEvent {
    data class OnTitleChanged(val title: String) : AddEditNoteEvent()
    data class OnDescriptionChanged(val description: String) : AddEditNoteEvent()
}
