package com.spksh.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.spksh.data.local.model.TransactionEntity
import com.spksh.data.local.model.TransactionResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TransactionEntity) : Long

    @Update
    suspend fun update(item: TransactionEntity)

    @Delete
    suspend fun delete(item: TransactionEntity)

    @Query("SELECT * FROM transaction_table ORDER BY localId ASC")
    fun getAllItemsFlow(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transaction_table ORDER BY localId ASC")
    suspend fun getAllItems(): List<TransactionEntity>

    @Query("SELECT * FROM transaction_table WHERE accountId = :accountId ORDER BY localId ASC")
    suspend fun getAllItemsByAccount(accountId: Long): List<TransactionEntity>

    @Query("SELECT * FROM transaction_table WHERE localId = :id LIMIT 1")
    suspend fun getItemById(id: Long): TransactionEntity?

//    @Query("SELECT * FROM transaction_table WHERE localId = :id LIMIT 1")
//    suspend fun getItemByIdFlow(id: Long): Flow<TransactionEntity?>

    @Query("SELECT * FROM transaction_table WHERE isSync = 0 ORDER BY localId ASC")
    suspend fun getUnsyncedItems(): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<TransactionEntity>) : List<Long>

    @Transaction
    @Query("SELECT * FROM transaction_table WHERE accountId = :accountId AND transactionDate >= :startDate AND transactionDate <= :endDate ORDER BY localId ASC")
    suspend fun getTransactionsByPeriod(
        accountId: Long,
        startDate: Long,
        endDate: Long
    ) : List<TransactionResponseEntity>

    @Transaction
    @Query("SELECT * FROM transaction_table WHERE accountId = :accountId AND transactionDate >= :startDate AND transactionDate <= :endDate ORDER BY localId ASC")
    fun getTransactionsByPeriodFlow(
        accountId: Long,
        startDate: Long,
        endDate: Long
    ) : Flow<List<TransactionResponseEntity>>
}