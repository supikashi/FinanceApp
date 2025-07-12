package com.spksh.transactions.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.spksh.transactions.di.DaggerTransactionsComponent
import com.spksh.transactions.di.TransactionsDependencies
import com.spksh.transactions.ui.screen.HistoryScreen
import com.spksh.transactions.ui.screen.IncomeScreen
import com.spksh.transactions.ui.screen.SpendingScreen
import com.spksh.transactions.ui.screen.TransactionScreen
import com.spksh.transactions.ui.view_model.IncomeHistoryViewModel
import com.spksh.transactions.ui.view_model.IncomeViewModel
import com.spksh.transactions.ui.view_model.SpendingViewModel
import com.spksh.transactions.ui.view_model.TransactionViewModel

fun NavGraphBuilder.incomeNav(
    navController: NavHostController,
    dependencies: TransactionsDependencies
) {
    val component = DaggerTransactionsComponent.factory()
        .create(dependencies)
    val factory = component.viewModelFactory()
    navigation<IncomeGraph>(startDestination = Income) {
        composable<Income> { backStackEntry ->
            val viewModel: IncomeViewModel = viewModel(factory = factory)
            IncomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<IncomeHistory> { backStackEntry ->
            val viewModel: IncomeHistoryViewModel = viewModel(factory = factory)
            HistoryScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<IncomeTransaction> { backStackEntry ->
            val transaction = backStackEntry.toRoute<IncomeTransaction>()
            val viewModel: TransactionViewModel = viewModel(factory = factory)
            TransactionScreen(
                viewModel = viewModel,
                navController = navController,
                transactionId = transaction.id,
                isIncome = true
            )
        }
    }
}