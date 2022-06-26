package com.tonotes.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tonotes.core.Constants
import com.tonotes.note_ui.home.HomeScreen
import com.tonotes.note_ui.note_detail.NoteDetailScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToNoteDetail = { id ->
                    navController.navigate(Screen.NoteDetailScreen.route + "?${Constants.NOTE_ID}=$id")
                }
            )
        }

        composable(
            route = Screen.NoteDetailScreen.route + "?${Constants.NOTE_ID}={${Constants.NOTE_ID}}",
            arguments = listOf(
                navArgument(Constants.NOTE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            NoteDetailScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}