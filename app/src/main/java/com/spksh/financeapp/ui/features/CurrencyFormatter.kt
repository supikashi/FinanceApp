package com.spksh.financeapp.ui.features

/**
 * форматирует валюту
 */
fun formatCurrency(currency: String) : String {
    return when(currency) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> currency
    }
}

/**
 * форматирует сумму
 */
fun formatSum(value: Double, currency: String): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        String.format("%.2f", value)
    } + " " + formatCurrency(currency)
}