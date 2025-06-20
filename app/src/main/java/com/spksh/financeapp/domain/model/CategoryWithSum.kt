package com.spksh.financeapp.domain.model

data class CategoryWithSum(
    val category: Category = Category(),
    val description: String? = null,
    val sum: Double = 0.0
)