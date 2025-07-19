package com.spksh.settings.di

import androidx.lifecycle.ViewModelProvider
import com.spksh.data.local.data_store.DataStoreRepositoryImpl
import com.spksh.di.CoreModule
import com.spksh.di.FactoryModule
import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.DataStoreRepository
import com.spksh.domain.repository.TransactionRepository
import dagger.Component
import jakarta.inject.Scope

@SettingsScope
@Component(
    modules = [
        SettingsUseCaseModule::class,
        SettingsViewModelModule::class,
        FactoryModule::class,
        CoreModule::class
    ],
    dependencies = [SettingsDependencies::class]
)
interface SettingsComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: SettingsDependencies): SettingsComponent
    }
}

interface SettingsDependencies {
    fun accountRepository() : AccountRepository
    fun transactionRepository() : TransactionRepository
    fun categoryRepository() : CategoryRepository

    fun context() : DataStoreRepository
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsScope