package com.spksh.data.di

import com.spksh.data.connectivity.NetworkConnectivityObserver
import com.spksh.data.local.data_store.DataStoreRepositoryImpl
import com.spksh.data.repository.AccountRepositoryImpl
import com.spksh.data.repository.CategoryRepositoryImpl
import com.spksh.data.repository.TransactionRepositoryImpl
import com.spksh.domain.connectivity.ConnectivityObserver
import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.DataStoreRepository
import com.spksh.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * DI-модуль, предоставляющий реализации интерфейсов источников данных
 */
@Module
interface RepositoryModule {
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

    @Singleton
    @Binds
    fun bindDataStoreRepository(
        impl: DataStoreRepositoryImpl
    ): DataStoreRepository
}