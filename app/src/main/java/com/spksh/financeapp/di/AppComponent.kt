package com.spksh.financeapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.spksh.account.di.AccountDependencies
import com.spksh.category.di.CategoryDependencies
import com.spksh.data.DataModule
import com.spksh.data.NetworkModule
import com.spksh.di.CoreModule
import com.spksh.di.FactoryModule
import com.spksh.domain.repository.AccountRepository
import com.spksh.transactions.di.TransactionsDependencies

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class,
    ViewModelModule::class,
    NetworkModule::class,
    CoreModule::class,
    FactoryModule::class
])
interface AppComponent :
    AccountDependencies,
    CategoryDependencies,
    TransactionsDependencies
{
    fun viewModelProviderFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }
}
