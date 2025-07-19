package com.spksh.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spksh.data.local.dao.AccountDao
import com.spksh.data.local.dao.CategoryDao
import com.spksh.data.local.dao.TransactionDao
import com.spksh.data.local.model.AccountEntity
import com.spksh.data.local.model.CategoryEntity
import com.spksh.data.local.model.TransactionEntity

@Database(entities = [AccountEntity::class, CategoryEntity::class, TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}