package com.spksh.transactions.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.spksh.transactions.di.DaggerTransactionsComponent
import com.spksh.transactions.di.TransactionsDependencies
import com.spksh.transactions.ui.screen.AnalysisScreen
import com.spksh.transactions.ui.screen.HistoryScreen
import com.spksh.transactions.ui.screen.SpendingScreen
import com.spksh.transactions.ui.screen.TransactionScreen
import com.spksh.transactions.ui.view_model.analysis.SpendingAnalysisViewModel
import com.spksh.transactions.ui.view_model.history.SpendingHistoryViewModel
import com.spksh.transactions.ui.view_model.transaction.SpendingViewModel
import com.spksh.transactions.ui.view_model.transaction.TransactionViewModel

fun NavGraphBuilder.spendingNav(
    navController: NavHostController,
    dependencies: TransactionsDependencies
) {
    val component = DaggerTransactionsComponent.factory()
        .create(dependencies)
    val factory = component.viewModelFactory()
    navigation<SpendingGraph>(startDestination = Spending) {

        composable<Spending> {
            val viewModel: SpendingViewModel = viewModel(factory = factory)
            SpendingScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<SpendingHistory> {
            val viewModel: SpendingHistoryViewModel = viewModel(factory = factory)
            HistoryScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable<SpendingTransaction> { entry ->
            val transaction = entry.toRoute<SpendingTransaction>()
            val viewModel: TransactionViewModel = viewModel(factory = factory)
            TransactionScreen(
                viewModel = viewModel,
                navController = navController,
                transactionId = transaction.id,
                isIncome = false
            )
        }
        composable<SpendingAnalysis> {
            val viewModel: SpendingAnalysisViewModel = viewModel(factory = factory)
            AnalysisScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}