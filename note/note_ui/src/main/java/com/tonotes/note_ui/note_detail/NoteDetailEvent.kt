package com.tonotes.note_ui.note_detail

sealed class NoteDetailEvent {
    object GetNoteDetail : NoteDetailEvent()
    object DeleteNote : NoteDetailEvent()
    data class OnMenuVisibilityChanged(val menuVisibility: Boolean) : NoteDetailEvent()
    data class OnDeleteDialogVisChanged(val deleteDialogVis: Boolean) : NoteDetailEvent()
}