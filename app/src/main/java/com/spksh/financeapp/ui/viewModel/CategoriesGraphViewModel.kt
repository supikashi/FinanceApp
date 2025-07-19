package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spksh.category.di.CategoryComponent
import com.spksh.category.di.CategoryDependencies
import com.spksh.category.di.DaggerCategoryComponent
import jakarta.inject.Inject

class CategoriesGraphViewModel @Inject constructor() : ViewModel() {
    var featureComponent: CategoryComponent? = null
    var factory: ViewModelProvider.Factory? = null
    fun setUp(dependencies: CategoryDependencies) {
        if (featureComponent == null) {
            featureComponent = DaggerCategoryComponent.factory().create(dependencies)
            factory = featureComponent?.viewModelFactory()
        }
    }
}