package com.spksh.account.di

import androidx.lifecycle.ViewModelProvider
import com.spksh.di.CoreModule
import com.spksh.di.FactoryModule
import com.spksh.domain.repository.AccountRepository
import dagger.Component
import jakarta.inject.Scope

@AccountScope
@Component(
    modules = [
        AccountViewModelModule::class,
        FactoryModule::class,
        CoreModule::class,
    ],
    dependencies = [AccountDependencies::class]
)
interface AccountComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: AccountDependencies): AccountComponent
    }
}

interface AccountDependencies {
    fun accountRepository(): AccountRepository
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AccountScope