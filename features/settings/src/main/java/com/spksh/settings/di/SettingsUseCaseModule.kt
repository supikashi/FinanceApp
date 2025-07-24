package com.spksh.settings.di

import com.spksh.domain.useCase.GetZoneIdUseCase
import dagger.Module
import dagger.Provides

@Module
object SettingsUseCaseModule {
    @SettingsScope
    @Provides
    fun provideGetZoneIdUseCase(): GetZoneIdUseCase =
        GetZoneIdUseCase()
}