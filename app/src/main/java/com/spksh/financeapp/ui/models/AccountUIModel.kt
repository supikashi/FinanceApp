package com.spksh.financeapp.ui.models

data class AccountUIModel(
    val id: Long = 0,
    val name: String = "",
    val emoji: String? = null,
    val balance: String = "",
    val currency: String = "",
    val changesList: List<AccountChangeUIModel> = emptyList()
)
