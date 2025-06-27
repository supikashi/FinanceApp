package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.GetCategoriesByTypeUseCase
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.CategoryScreenState
import com.spksh.financeapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана категорий
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<CategoryScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<CategoryScreenState>> = _uiState

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        try {
            val categories = getCategoriesByTypeUseCase(false).map { it.toUiModel() }
            _uiState.value = UiState.Success(data = CategoryScreenState(categories = categories))
        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }
}