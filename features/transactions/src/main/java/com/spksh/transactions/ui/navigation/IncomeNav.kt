package com.spksh.transactions.ui.navigation

import android.util.Log
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
import com.spksh.transactions.ui.screen.IncomeScreen
import com.spksh.transactions.ui.screen.TransactionScreen
import com.spksh.transactions.ui.view_model.analysis.IncomeAnalysisViewModel
import com.spksh.transactions.ui.view_model.history.IncomeHistoryViewModel
import com.spksh.transactions.ui.view_model.transaction.IncomeViewModel
import com.spksh.transactions.ui.view_model.transaction.TransactionViewModel

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
            Log.i("my_tag", viewModel.toString())
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
        composable<IncomeAnalysis> {
            val viewModel: IncomeAnalysisViewModel = viewModel(factory = factory)
            AnalysisScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}