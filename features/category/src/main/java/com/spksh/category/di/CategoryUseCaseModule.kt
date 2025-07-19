package com.spksh.category.di

import com.spksh.domain.repository.CategoryRepository
import com.spksh.domain.useCase.GetCategoriesByTypeFlowUseCase
import com.spksh.domain.useCase.LoadCategoriesFromNetworkUseCase
import dagger.Module
import dagger.Provides

@Module
object CategoryUseCaseModule {
    @CategoryScope
    @Provides
    fun provideGetCategoriesByTypeUseCase(repository: CategoryRepository): GetCategoriesByTypeFlowUseCase =
        GetCategoriesByTypeFlowUseCase(repository)

    @CategoryScope
    @Provides
    fun provideLoadCategoriesFromNetworkUseCase(repository: CategoryRepository): LoadCategoriesFromNetworkUseCase =
        LoadCategoriesFromNetworkUseCase(repository)
}