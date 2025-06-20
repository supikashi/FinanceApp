package com.spksh.financeapp.ui.state

import com.spksh.financeapp.ui.model.CategoryUIModel

sealed class CategoryUiState {
    object Loading : CategoryUiState()

    data class Error(val message: String) : CategoryUiState()

    data class Success(val categories: List<CategoryUIModel>) : CategoryUiState()
}