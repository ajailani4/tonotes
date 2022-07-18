package com.tonotes.note_ui.home

sealed class HomeEvent {
    object GetNotes : HomeEvent()
    object GetAccessToken : HomeEvent()
    data class OnSearchQueryChanged(val searchQuery: String) : HomeEvent()
    data class OnLoginAlertDialogVisChanged(val loginAlertDialogVis: Boolean) : HomeEvent()
}
