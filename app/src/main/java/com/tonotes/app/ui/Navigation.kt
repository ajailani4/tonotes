package com.tonotes.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tonotes.core.Constants.NavArgument.NOTE_ID
import com.tonotes.note_ui.add_edit_note.AddEditNoteScreen
import com.tonotes.note_ui.home.HomeScreen
import com.tonotes.note_ui.note_detail.NoteDetailScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToNoteDetail = { id ->
                    navController.navigate(Screen.NoteDetailScreen.route + "/$id")
                },
                onNavigateToAddOrEditNote = { id ->
                    navController.navigate(Screen.AddEditNoteScreen.route + "/$id")
                }
            )
        }

        composable(
            route = Screen.NoteDetailScreen.route + "/{$NOTE_ID}",
            arguments = listOf(
                navArgument(NOTE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            NoteDetailScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.AddEditNoteScreen.route + "/{$NOTE_ID}",
            arguments = listOf(
                navArgument(NOTE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            AddEditNoteScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}