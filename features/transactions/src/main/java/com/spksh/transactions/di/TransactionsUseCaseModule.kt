package com.spksh.transactions.di

import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.useCase.GetCategoriesByTypeFlowUseCase
import com.spksh.domain.useCase.GetCategoriesFlowUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.LoadCategoriesFromNetworkUseCase
import dagger.Module
import dagger.Provides

@Module
object TransactionsUseCaseModule {
    @TransactionsScope
    @Provides
    fun provideGetTodayUseCase(): GetTodayUseCase =
        GetTodayUseCase()

    @TransactionsScope
    @Provides
    fun provideGetZoneIdUseCase(): GetZoneIdUseCase =
        GetZoneIdUseCase()

    @TransactionsScope
    @Provides
    fun provideGetCategoriesByTypeUseCase(repository: CategoryRepository): GetCategoriesByTypeFlowUseCase =
        GetCategoriesByTypeFlowUseCase(repository)

    @TransactionsScope
    @Provides
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesFlowUseCase =
        GetCategoriesFlowUseCase(repository)

    @TransactionsScope
    @Provides
    fun provideLoadCategoriesFromNetworkUseCase(repository: CategoryRepository): LoadCategoriesFromNetworkUseCase =
        LoadCategoriesFromNetworkUseCase(repository)
}