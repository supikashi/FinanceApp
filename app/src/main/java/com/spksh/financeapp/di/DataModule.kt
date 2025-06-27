package com.spksh.financeapp.di

import com.spksh.financeapp.data.connectivity.NetworkConnectivityObserver
import com.spksh.financeapp.data.remote.repository.AccountRepositoryImpl
import com.spksh.financeapp.data.remote.repository.CategoryRepositoryImpl
import com.spksh.financeapp.data.remote.repository.TransactionRepositoryImpl
import com.spksh.financeapp.domain.connectivity.ConnectivityObserver
import com.spksh.financeapp.domain.repository.AccountRepository
import com.spksh.financeapp.domain.repository.CategoryRepository
import com.spksh.financeapp.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DI-модуль, предоставляющий реализации интерфейсов источников данных
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(
        impl: NetworkConnectivityObserver
    ): ConnectivityObserver
}