package com.example.journal_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.journal_app.feature.note.Note

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = DefineScreens.NotePage.route
    ) {
        composable(route = DefineScreens.NotePage.route) {
            Note(navHostController = navHostController)
        }
    }
}