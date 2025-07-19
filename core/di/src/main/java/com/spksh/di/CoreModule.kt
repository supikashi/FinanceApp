package com.spksh.di

import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.DataStoreRepository
import com.spksh.domain.repository.TransactionRepository
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.domain.useCase.SynchronizeDatabaseUseCase
import com.spksh.domain.useCase.SynchronizeUpdatedUseCase
import dagger.Module
import dagger.Provides

@Module
object CoreModule {
    @Provides
    fun provideGetAccountsFlowUseCase(repository: AccountRepository): GetAccountsFlowUseCase =
        GetAccountsFlowUseCase(repository)

    @Provides
    fun provideLoadAccountsUseCase(repository: AccountRepository): LoadAccountsUseCase =
        LoadAccountsUseCase(repository)

    @Provides
    fun provideSynchronizeDatabaseUseCase(
        accountRepository: AccountRepository,
        transactionRepository: TransactionRepository,
        categoryRepository: CategoryRepository,
        dataStoreRepository: DataStoreRepository
    ): SynchronizeDatabaseUseCase =
        SynchronizeDatabaseUseCase(
            accountRepository,
            transactionRepository,
            categoryRepository,
            dataStoreRepository
        )

    @Provides
    fun provideSynchronizeUpdatedUseCase(
        accountRepository: AccountRepository,
        transactionRepository: TransactionRepository,
        categoryRepository: CategoryRepository
    ): SynchronizeUpdatedUseCase =
        SynchronizeUpdatedUseCase(
            accountRepository,
            transactionRepository,
            categoryRepository
        )
}