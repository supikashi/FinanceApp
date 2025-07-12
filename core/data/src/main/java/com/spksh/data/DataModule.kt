package com.spksh.data

import com.spksh.data.connectivity.NetworkConnectivityObserver
import com.spksh.data.remote.repository.AccountRepositoryImpl
import com.spksh.data.remote.repository.CategoryRepositoryImpl
import com.spksh.data.remote.repository.TransactionRepositoryImpl
import com.spksh.domain.connectivity.ConnectivityObserver
import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * DI-модуль, предоставляющий реализации интерфейсов источников данных
 */
@Module
interface DataModule {
    @Singleton
    @Binds
    fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository

    @Singleton
    @Binds
    fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Singleton
    @Binds
    fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Singleton
    @Binds
    fun bindConnectivityObserver(
        impl: NetworkConnectivityObserver
    ): ConnectivityObserver
}