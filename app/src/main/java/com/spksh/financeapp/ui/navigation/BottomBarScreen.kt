package com.spksh.financeapp.ui.navigation

import com.spksh.financeapp.R

/**
 * Модель экрана нижней навигационной панели
 */
data class BottomBarScreen<T : Any>(val route: T, val title: String, val icon: Int) {
    companion object {
        val getScreensLists = listOf(
            BottomBarScreen(SpendingGraph, "Расходы", R.drawable.downtrend),
            BottomBarScreen(IncomeGraph, "Доходы", R.drawable.uptrend),
            BottomBarScreen(AccountGraph, "Счет", R.drawable.calculator),
            BottomBarScreen(Categories, "Статьи", R.drawable.bar_chart_side),
            BottomBarScreen(Settings, "Настройки", R.drawable.screw)
        )
    }
}