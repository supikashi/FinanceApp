package com.spksh.financeapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.spksh.account.di.AccountDependencies
import com.spksh.category.di.CategoryDependencies
import com.spksh.data.di.LocalDataModule
import com.spksh.data.di.RepositoryModule
import com.spksh.data.di.RemoteDataModule
import com.spksh.di.CoreModule
import com.spksh.di.FactoryModule
import com.spksh.financeapp.FinanceApp
import com.spksh.financeapp.MainActivity
import com.spksh.settings.di.SettingsDependencies
import com.spksh.transactions.di.TransactionsDependencies

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RepositoryModule::class,
    ViewModelModule::class,
    RemoteDataModule::class,
    LocalDataModule::class,
    CoreModule::class,
    FactoryModule::class
])
interface AppComponent :
    AccountDependencies,
    CategoryDependencies,
    TransactionsDependencies,
    SettingsDependencies
{
    fun injectToApplication(application: FinanceApp)
    fun injectToActivity(activity: MainActivity)
    fun viewModelProviderFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }
}
