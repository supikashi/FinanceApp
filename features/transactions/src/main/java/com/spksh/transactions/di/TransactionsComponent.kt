package com.spksh.transactions.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.spksh.di.CoreModule
import com.spksh.di.FactoryModule
import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.repository.TransactionRepository
import dagger.Component
import jakarta.inject.Scope


@TransactionsScope
@Component(
    modules = [
        TransactionsUseCaseModule::class,
        TransactionsViewModelModule::class,
        FactoryModule::class,
        CoreModule::class
    ],
    dependencies = [
        TransactionsDependencies::class
    ]
)
interface TransactionsComponent {

    @TransactionsScope
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: TransactionsDependencies): TransactionsComponent
    }
}

interface TransactionsDependencies {
    fun accountRepository() : AccountRepository
    fun transactionsRepository() : TransactionRepository
    fun categoryRepository() : CategoryRepository
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionsScope