package com.spksh.account.di

import com.spksh.domain.repository.TransactionRepository
import com.spksh.domain.useCase.GetTodayUseCase
import com.spksh.domain.useCase.GetTransactionsByPeriodFlowUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import dagger.Module
import dagger.Provides

@Module
object AccountUseCaseModule {
    @AccountScope
    @Provides
    fun provideGetTodayUseCase(): GetTodayUseCase =
        GetTodayUseCase()

    @AccountScope
    @Provides
    fun provideGetZoneIdUseCase(): GetZoneIdUseCase =
        GetZoneIdUseCase()
    @AccountScope
    @Provides
    fun provideGetTransactionsByPeriodFlowUseCase(repository: TransactionRepository): GetTransactionsByPeriodFlowUseCase =
        GetTransactionsByPeriodFlowUseCase(repository)
}