package com.spksh.financeapp.data.remote.model

import com.spksh.financeapp.domain.model.Category
import kotlinx.serialization.Serializable

/**
 * DTO-модель категории, используемая для парсинга данных из API.
 */
@Serializable
data class CategoryDTO(
    val id: Long = 0,
    val name: String = "",
    val emoji: String = "",
    val isIncome: Boolean = false
) {
    fun toCategory() = Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}