package com.spksh.financeapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SpendingGraph

@Serializable
object IncomeGraph

@Serializable
object AccountGraph

@Serializable
object Spending

@Serializable
object Income

@Serializable
object Account

@Serializable
object Categories

@Serializable
object Settings

@Serializable
object SpendingHistory

@Serializable
object IncomeHistory

@Serializable
data class AccountUpdate(val id: Long)