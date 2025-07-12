package com.spksh.account.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.spksh.account.di.AccountDependencies
import com.spksh.account.di.DaggerAccountComponent
import com.spksh.account.ui.screen.AccountScreen
import com.spksh.account.ui.screen.AccountUpdateScreen
import com.spksh.account.ui.view_model.AccountUpdateViewModel
import com.spksh.account.ui.view_model.AccountViewModel

fun NavGraphBuilder.accountNav(
    navController: NavHostController,
    dependencies: AccountDependencies
) {
    val component = DaggerAccountComponent.factory()
        .create(dependencies)
    val factory = component.viewModelFactory()
    navigation<AccountGraph>(startDestination = Account) {
        composable<Account> {
            val viewModel: AccountViewModel = viewModel(factory = factory)
            AccountScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<AccountUpdate> {
            val viewModel: AccountUpdateViewModel = viewModel(factory = factory)
            AccountUpdateScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}