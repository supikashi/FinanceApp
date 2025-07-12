package com.spksh.di

import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
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
}