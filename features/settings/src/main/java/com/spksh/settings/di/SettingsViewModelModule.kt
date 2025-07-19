package com.spksh.settings.di

import androidx.lifecycle.ViewModel
import com.spksh.di.ViewModelKey
import com.spksh.settings.ui.view_model.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SettingsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
}