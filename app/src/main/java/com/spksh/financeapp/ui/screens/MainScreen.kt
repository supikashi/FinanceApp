package com.spksh.financeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spksh.financeapp.ui.navigation.Account
import com.spksh.financeapp.ui.navigation.BottomBarScreen
import com.spksh.financeapp.ui.navigation.Categories
import com.spksh.financeapp.ui.navigation.Income
import com.spksh.financeapp.ui.navigation.Settings
import com.spksh.financeapp.ui.navigation.Spending
import com.spksh.financeapp.ui.theme.FinanceAppTheme

@Composable
fun MainScreen() {
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
            AppNavigation(navController, Modifier.padding(innerPadding).background(MaterialTheme.colorScheme.background))
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
    NavHost(navController, startDestination = Spending, modifier) {
        composable<Spending> {
            SpendingScreen(
                sumText = SpendingData.sumText,
                categoriesList = SpendingData.categoriesList
            )
        }
        composable<Income> {
            IncomeScreen(
                sumText = IncomeData.sumText,
                categoriesList = IncomeData.categoriesList
            )
        }
        composable<Account> {
            AccountScreen(
                accountsList = AccountData.accountsList
            )
        }
        composable<Categories> {
            CategoriesScreen(
                categoriesList = CategoriesData.categoriesList
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