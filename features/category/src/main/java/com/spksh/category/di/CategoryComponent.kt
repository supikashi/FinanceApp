package com.spksh.category.di

import androidx.lifecycle.ViewModelProvider
import com.spksh.di.CoreModule
import com.spksh.di.FactoryModule
import com.spksh.domain.repository.CategoryRepository
import dagger.Component
import jakarta.inject.Scope

@CategoryScope
@Component(
    modules = [
        CategoryUseCaseModule::class,
        CategoryViewModelModule::class,
        FactoryModule::class,
        CoreModule::class
    ],
    dependencies = [CategoryDependencies::class]
)
interface CategoryComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: CategoryDependencies): CategoryComponent
    }
}

interface CategoryDependencies {
    fun categoryRepository() : CategoryRepository
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CategoryScope