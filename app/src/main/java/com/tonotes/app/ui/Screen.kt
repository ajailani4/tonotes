package com.tonotes.app.ui

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object NoteDetailScreen : Screen("note_detail_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
}
