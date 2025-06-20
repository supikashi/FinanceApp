package com.spksh.financeapp

import com.spksh.financeapp.ui.model.AccountUIModel
import com.spksh.financeapp.ui.model.CategoryUIModel
import com.spksh.financeapp.ui.model.CategoryWithSumUIModel
import com.spksh.financeapp.ui.model.TransactionUiModel

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
    val incomeSumText = "600 000 ₽"
    val incomeCategoriesList: List<CategoryWithSumUIModel> = listOf(
        CategoryWithSumUIModel(
            id = 1,
            name = "Зарплата",
            sum = "500 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 2,
            name = "Подработка",
            sum = "100 000 ₽",
        )
    )
    val spendingSumText = "436 558 ₽"
    val spendingCategoriesList: List<CategoryWithSumUIModel> = listOf(
        CategoryWithSumUIModel(
            id = 1,
            name = "Аренда квартиры",
            emoji = "🏠",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 2,
            name = "Одежда",
            emoji = "👗",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 3,
            name = "На собачку",
            emoji = "🐶",
            sum = "100 000 ₽",
            description = "Джек"
        ),
        CategoryWithSumUIModel(
            id = 4,
            name = "На собачку",
            emoji = "🐶",
            sum = "100 000 ₽",
            description = "Энни"
        ),CategoryWithSumUIModel(
            id = 5,
            name = "Ремонт квартиры",
            emoji = "РК",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 6,
            name = "Продукты",
            emoji = "🍭",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 7,
            name = "Спортзал",
            emoji = "🏋",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        ),
        CategoryWithSumUIModel(
            id = 8,
            name = "Медицина",
            emoji = "💊",
            sum = "100 000 ₽",
        )
    )
    val historyStartDate = "19.06.2025"
    val historyEndDate = "21.07.2077"
    val historySum = "123 456 ₽"
    val transactions = listOf(
        TransactionUiModel(
            category = CategoryUIModel(
                name = "Ремонт квартиры",
                emoji = "РК"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22",
            comment = "Фурнитура для дверей"
        ),
        TransactionUiModel(
            category = CategoryUIModel(
                name = "На собачку",
                emoji = "🐶"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22"
        ),
        TransactionUiModel(
            category = CategoryUIModel(
                name = "На собачку",
                emoji = "🐶"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22"
        ),
        TransactionUiModel(
            category = CategoryUIModel(
                name = "На собачку",
                emoji = "🐶"
            ),
            amount = "100 000 ₽",
            transactionDate = "22:22"
        )
    )
}