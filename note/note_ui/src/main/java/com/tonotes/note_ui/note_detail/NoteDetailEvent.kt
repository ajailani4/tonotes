package com.tonotes.note_ui.note_detail

sealed class NoteDetailEvent {
    data class OnMenuVisibilityChanged(val menuVisibility: Boolean) : NoteDetailEvent()
}