package com.spksh.category.ui.navigation

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.spksh.category.di.CategoryDependencies
import com.spksh.category.di.DaggerCategoryComponent
import com.spksh.category.ui.screen.CategoryScreen
import com.spksh.category.ui.view_model.CategoryViewModel

fun NavGraphBuilder.categoryNav(
    navController: NavHostController,
    dependencies: CategoryDependencies
) {
    val component = DaggerCategoryComponent.factory()
        .create(dependencies)
    val factory = component.viewModelFactory()
    navigation<CategoriesGraph>(Categories) {
        composable<Categories> {
            val viewModel: CategoryViewModel = viewModel(
                factory = factory
            )
            remember {
                Log.i("my_tag", viewModel.toString())
                Log.i("my_tag", factory.toString())
            }
            CategoryScreen(
                viewModel = viewModel,
            )
        }
    }

}
