package com.tonotes.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tonotes.account_ui.login.LoginScreen
import com.tonotes.account_ui.register.RegisterScreen
import com.tonotes.core.util.Constants.NavArgument
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
                onNavigateToAddEditNote = { id ->
                    navController.navigate(Screen.AddEditNoteScreen.route + "/$id")
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                }
            )
        }

        composable(
            route = Screen.NoteDetailScreen.route + "/{${NavArgument.NOTE_ID}}",
            arguments = listOf(
                navArgument(NavArgument.NOTE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            NoteDetailScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToAddEditNote = { id ->
                    navController.navigate(Screen.AddEditNoteScreen.route + "/$id")
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true

                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.AddEditNoteScreen.route + "/{${NavArgument.NOTE_ID}}",
            arguments = listOf(
                navArgument(NavArgument.NOTE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            AddEditNoteScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true

                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToRegister = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true

                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToHome = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true

                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}