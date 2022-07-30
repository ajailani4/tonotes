package com.tonotes.note_ui.home

sealed class HomeEvent {
    object Idle : HomeEvent()
    object GetNotes : HomeEvent()
    object GetAccessToken : HomeEvent()
    object BackUpNotes : HomeEvent()
    object GetSelectedBackupType : HomeEvent()
    object SyncNotes : HomeEvent()
    data class OnSearchQueryChanged(val searchQuery: String) : HomeEvent()
    data class OnLoginAlertDialogVisChanged(val loginAlertDialogVis: Boolean) : HomeEvent()
    data class OnBackUpNotesDialogVisChanged(val backUpNotesDialogVis: Boolean) : HomeEvent()
    data class OnBackupTypeSelected(val selectedBackupTypes: Int) : HomeEvent()
}
