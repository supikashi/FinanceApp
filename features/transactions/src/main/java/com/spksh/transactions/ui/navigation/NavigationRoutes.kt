package com.spksh.transactions.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SpendingGraph

@Serializable
object IncomeGraph

@Serializable
object Spending

@Serializable
object Income

@Serializable
object SpendingHistory

@Serializable
object IncomeHistory

@Serializable
data class IncomeTransaction(val id: Long?)

@Serializable
data class SpendingTransaction(val id: Long?)