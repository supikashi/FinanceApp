package com.spksh.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.spksh.data.local.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CategoryEntity) : Long

    @Update
    suspend fun update(item: CategoryEntity)

    @Delete
    suspend fun delete(item: CategoryEntity)

    @Query("SELECT * FROM category_table ORDER BY id ASC")
    fun getAllItemsFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category_table ORDER BY id ASC")
    suspend fun getAllItems(): List<CategoryEntity>

    @Query("SELECT * FROM category_table WHERE isIncome = :isIncome ORDER BY id ASC")
    fun getAllItemsByTypeFlow(isIncome: Boolean): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category_table WHERE isIncome = :isIncome ORDER BY id ASC")
    suspend fun getAllItemsByType(isIncome: Boolean): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<CategoryEntity>) : List<Long>
}