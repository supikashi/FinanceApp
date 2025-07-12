package com.spksh.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.settingsNav(
    navController: NavHostController
) {
    navigation<SettingsGraph>(startDestination = Settings) {
        composable<Settings> {
            SettingsScreen()
        }
    }
}