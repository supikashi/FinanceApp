package com.spksh.financeapp.ui.navigation

import com.spksh.account.ui.navigation.AccountGraph
import com.spksh.category.ui.navigation.CategoriesGraph
import com.spksh.financeapp.R
import com.spksh.settings.ui.SettingsGraph
import com.spksh.transactions.ui.navigation.IncomeGraph
import com.spksh.transactions.ui.navigation.SpendingGraph

/**
 * Модель экрана нижней навигационной панели
 */
data class BottomBarScreen<T : Any>(val route: T, val title: String, val icon: Int) {
    companion object {
        val getScreensLists = listOf(
            BottomBarScreen(SpendingGraph, "Расходы", R.drawable.downtrend),
            BottomBarScreen(IncomeGraph, "Доходы", R.drawable.uptrend),
            BottomBarScreen(AccountGraph, "Счет", R.drawable.calculator),
            BottomBarScreen(CategoriesGraph, "Статьи", R.drawable.bar_chart_side),
            BottomBarScreen(SettingsGraph, "Настройки", R.drawable.screw)
        )
    }
}