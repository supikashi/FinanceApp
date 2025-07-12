package com.spksh.transactions.di

import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.TransactionRepository
import com.spksh.domain.useCase.GetCategoriesByTypeUseCase
import com.spksh.domain.useCase.GetCategoriesUseCase
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.transactions.domain.use_case.GetTransactionsByPeriodUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
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
    fun provideGetCategoriesByTypeUseCase(repository: CategoryRepository): GetCategoriesByTypeUseCase =
        GetCategoriesByTypeUseCase(repository)

    @TransactionsScope
    @Provides
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase =
        GetCategoriesUseCase(repository)
}