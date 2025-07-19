package com.spksh.settings.di

import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.TransactionRepository
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.SynchronizeDatabaseUseCase
import dagger.Module
import dagger.Provides

@Module
object SettingsUseCaseModule {
    @SettingsScope
    @Provides
    fun provideGetZoneIdUseCase(): GetZoneIdUseCase =
        GetZoneIdUseCase()
}