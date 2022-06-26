package com.tonotes.app.ui

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")

    object NoteDetailScreen : Screen("note_detail_screen")
}
