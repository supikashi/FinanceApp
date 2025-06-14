package com.spksh.financeapp.ui.navigation

import com.spksh.financeapp.R

data class BottomBarScreen<T : Any>(val route: T, val title: String, val icon: Int) {
    companion object {
        val getScreensLists = listOf(
            BottomBarScreen(Spending, "Расходы", R.drawable.downtrend),
            BottomBarScreen(Income, "Доходы", R.drawable.uptrend),
            BottomBarScreen(Account, "Счет", R.drawable.calculator),
            BottomBarScreen(Categories, "Статьи", R.drawable.bar_chart_side),
            BottomBarScreen(Settings, "Настройки", R.drawable.screw)
        )
    }
}