package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetCategoriesByTypeUseCase
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        fetchCategory()
    }

    fun fetchCategory() = viewModelScope.launch {
        _uiState.value = CategoryUiState.Loading
        try {
            val categories = getCategoriesByTypeUseCase(false).map { it.toUiModel() }
            _uiState.value = CategoryUiState.Success(categories)
        } catch (e: Throwable) {
            _uiState.value = CategoryUiState.Error(e.message ?: "Unknown error")
        }
    }
}