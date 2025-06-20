package com.spksh.financeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.spksh.financeapp.MockData
import com.spksh.financeapp.domain.ConnectivityObserver
import com.spksh.financeapp.ui.components.NoInternetBanner
import com.spksh.financeapp.ui.navigation.Account
import com.spksh.financeapp.ui.navigation.BottomBarScreen
import com.spksh.financeapp.ui.navigation.Categories
import com.spksh.financeapp.ui.navigation.Income
import com.spksh.financeapp.ui.navigation.IncomeGraph
import com.spksh.financeapp.ui.navigation.IncomeHistory
import com.spksh.financeapp.ui.navigation.Settings
import com.spksh.financeapp.ui.navigation.Spending
import com.spksh.financeapp.ui.navigation.SpendingGraph
import com.spksh.financeapp.ui.navigation.SpendingHistory
import com.spksh.financeapp.ui.theme.FinanceAppTheme
import com.spksh.financeapp.ui.viewModel.AccountViewModel
import com.spksh.financeapp.ui.viewModel.CategoryViewModel
import com.spksh.financeapp.ui.viewModel.IncomeHistoryViewModel
import com.spksh.financeapp.ui.viewModel.IncomeViewModel
import com.spksh.financeapp.ui.viewModel.NetworkViewModel
import com.spksh.financeapp.ui.viewModel.SpendingHistoryViewModel
import com.spksh.financeapp.ui.viewModel.SpendingViewModel

@Composable
fun MainScreen() {
    val vm: NetworkViewModel = hiltViewModel()
    val status by vm.status.collectAsState()
    val navController = rememberNavController()
    FinanceAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomBar(navController)
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoInternetBanner(isVisible = status != ConnectivityObserver.Status.Available)
                AppNavigation(navController, Modifier.background(MaterialTheme.colorScheme.background))
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        modifier = Modifier.height(104.dp)
    ) {
        BottomBarScreen.getScreensLists.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline,
                )
            )
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = SpendingGraph, modifier) {
        navigation<SpendingGraph>(startDestination = Spending) {
            composable<Spending> {
                val viewModel: SpendingViewModel = hiltViewModel()
                SpendingScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable<SpendingHistory> {
                val viewModel: SpendingHistoryViewModel = hiltViewModel()
                HistoryScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }
        }
        navigation<IncomeGraph>(startDestination = Income) {
            composable<Income> {
                val viewModel: IncomeViewModel = hiltViewModel()
                IncomeScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable<IncomeHistory> {
                val viewModel: IncomeHistoryViewModel = hiltViewModel()
                HistoryScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        composable<Account> {
            val viewModel: AccountViewModel = hiltViewModel()
            AccountScreen(
                viewModel = viewModel
            )
        }
        composable<Categories> {
            val viewModel: CategoryViewModel = hiltViewModel()
            CategoryScreen(
                viewModel = viewModel
            )
        }
        composable<Settings> {
            SettingsScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}