package com.spksh.financeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spksh.account.ui.navigation.accountNav
import com.spksh.category.ui.navigation.categoryNav
import com.spksh.domain.connectivity.ConnectivityObserver
import com.spksh.financeapp.di.AppComponent
import com.spksh.financeapp.ui.navigation.BottomBarScreen
import com.spksh.financeapp.ui.viewModel.NetworkViewModel
import com.spksh.settings.ui.navigation.settingsNav
import com.spksh.transactions.ui.navigation.SpendingGraph
import com.spksh.transactions.ui.navigation.incomeNav
import com.spksh.transactions.ui.navigation.spendingNav
import com.spksh.ui.components.NoInternetBanner
import com.spksh.ui.theme.FinanceAppTheme

@Composable
fun MainScreen(
    viewModelFactory: ViewModelProvider.Factory,
    appComponent: AppComponent
) {
    val viewModel: NetworkViewModel = viewModel(factory = viewModelFactory)
    val status by viewModel.status.collectAsState()
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
                AppNavigation(
                    navController,
                    Modifier.background(MaterialTheme.colorScheme.background),
                    appComponent
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        //modifier = Modifier.height(84.dp)
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
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier,
    dependencies: AppComponent
) {
    NavHost(navController, startDestination = SpendingGraph, modifier) {
        spendingNav(navController, dependencies)
        incomeNav(navController, dependencies)
        accountNav(navController, dependencies)
        categoryNav(navController, dependencies)
        settingsNav(navController, dependencies)
    }
}
