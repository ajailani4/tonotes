package com.tonotes.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tonotes.note_ui.home.HomeScreen
import com.tonotes.note_ui.note_detail.NoteDetailScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToNoteDetail = {
                    navController.navigate(Screen.NoteDetailScreen.route)
                }
            )
        }

        composable(route = Screen.NoteDetailScreen.route) {
            NoteDetailScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}