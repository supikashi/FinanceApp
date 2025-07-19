package com.spksh.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spksh.domain.model.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey val id: Long = 0,
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