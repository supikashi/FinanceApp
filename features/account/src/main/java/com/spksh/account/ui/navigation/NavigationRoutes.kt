package com.spksh.account.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object AccountGraph

@Serializable
object Account

@Serializable
data class AccountUpdate(val id: Long)