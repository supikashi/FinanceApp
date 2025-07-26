package com.spksh.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.spksh.data.local.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AccountEntity) : Long

    @Update
    suspend fun update(item: AccountEntity)

    @Delete
    suspend fun delete(item: AccountEntity)

    @Query("SELECT * FROM account_table ORDER BY localId ASC")
    fun getAllItemsFlow(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM account_table ORDER BY localId ASC")
    suspend fun getAllItems(): List<AccountEntity>

    @Query("SELECT * FROM account_table WHERE isSync = 0 ORDER BY localId ASC")
    suspend fun getUnsyncedItems(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<AccountEntity>) : List<Long>
}