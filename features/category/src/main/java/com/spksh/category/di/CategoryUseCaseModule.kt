package com.spksh.category.di

import com.spksh.domain.repository.CategoryRepository
import com.spksh.category.domain.use_case.FilterCategoriesByNameUseCase
import com.spksh.domain.useCase.GetCategoriesByTypeUseCase
import dagger.Module
import dagger.Provides

@Module
object CategoryUseCaseModule {
    @CategoryScope
    @Provides
    fun provideGetCategoriesByTypeUseCase(repository: CategoryRepository): GetCategoriesByTypeUseCase =
        GetCategoriesByTypeUseCase(repository)
}