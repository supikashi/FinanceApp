package com.spksh.financeapp

import com.spksh.ui.model.AccountUIModel
import com.spksh.ui.model.CategoryUIModel
import com.spksh.transactions.ui.model.TransactionResponseUiModel

/**
 * Объект с моковыми данными
 */
object MockData {
    val accountsList: List<AccountUIModel> = listOf(
        AccountUIModel(
            id = 0,
            name = "Основной счет",
            balance = "-670 000 ₽",
            currency = "₽",
        )
    )
    val categoriesList: List<CategoryUIModel> = listOf(
        CategoryUIModel(
            id = 1,
            name = "Аренда квартиры",
            emoji = "🏠",
        ),
        CategoryUIModel(
            id = 2,
            name = "Одежда",
            emoji = "👗",
        ),
        CategoryUIModel(
            id = 3,
            name = "На собачку",
            emoji = "🐶"
        ),
        CategoryUIModel(
            id = 4,
            name = "На собачку",
            emoji = "🐶"
        ), CategoryUIModel(
            id = 5,
            name = "Ремонт квартиры",
            emoji = "РК",
        ),
        CategoryUIModel(
            id = 6,
            name = "Продукты",
            emoji = "🍭",
        ),
        CategoryUIModel(
            id = 7,
            name = "Спортзал",
            emoji = "🏋",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
        CategoryUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
        ),
    )

    val historyStartDate = "19.06.2025"
    val historyEndDate = "21.07.2077"
    val historySum = "123 456 ₽"
    val transactions = listOf(
        TransactionResponseUiModel(
            category = CategoryUIModel(
                name = "Ремонт квартиры",
                emoji = "РК"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22",
            comment = "Фурнитура для дверей"
        ),
        TransactionResponseUiModel(
            category = CategoryUIModel(
                name = "На собачку",
                emoji = "🐶"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22"
        ),
        TransactionResponseUiModel(
            category = CategoryUIModel(
                name = "На собачку",
                emoji = "🐶"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22"
        ),
        TransactionResponseUiModel(
            category = CategoryUIModel(
                name = "На собачку",
                emoji = "🐶"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22"
        )
    )
}