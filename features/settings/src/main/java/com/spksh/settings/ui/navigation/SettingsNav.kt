package com.spksh.settings.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.spksh.settings.di.DaggerSettingsComponent
import com.spksh.settings.di.SettingsDependencies
import com.spksh.settings.ui.screen.SettingsScreen
import com.spksh.settings.ui.view_model.SettingsViewModel

fun NavGraphBuilder.settingsNav(
    navController: NavHostController,
    dependencies: SettingsDependencies,
    version: String
) {
    val component = DaggerSettingsComponent.factory()
        .create(dependencies)
    val factory = component.viewModelFactory()
    navigation<SettingsGraph>(startDestination = Settings) {
        composable<Settings> {
            val viewModel: SettingsViewModel = viewModel(factory = factory)
            SettingsScreen(viewModel, version = version)
        }
    }
}