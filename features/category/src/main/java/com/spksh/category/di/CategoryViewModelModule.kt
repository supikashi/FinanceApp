package com.spksh.category.di

import androidx.lifecycle.ViewModel
import com.spksh.category.ui.view_model.CategoryViewModel
import com.spksh.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CategoryViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel
}