package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.financeapp.domain.useCase.FilterCategoriesByNameUseCase
import com.spksh.financeapp.domain.useCase.GetCategoriesByTypeUseCase
import com.spksh.financeapp.ui.model.toUiModel
import com.spksh.financeapp.ui.state.CategoryScreenState
import com.spksh.financeapp.ui.state.UiState
import com.spksh.financeapp.ui.utils.multipleFetch
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана категорий
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val filterCategoriesByNameUseCase: FilterCategoriesByNameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<CategoryScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<CategoryScreenState>> = _uiState

    private var filterJob: Job? = null

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        try {
            multipleFetch(
                fetch = {
                    val categories = getCategoriesByTypeUseCase(false).map { it.toUiModel() }
                    _uiState.value = UiState.Success(
                        data = CategoryScreenState(
                            categories = categories,
                            filteredCategories = categories
                        )
                    )
                }
            )
        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }

    fun filterCategories(name: String) {
        filterJob?.cancel()

        val currentState = _uiState.value as? UiState.Success<CategoryScreenState> ?: return

        filterJob = viewModelScope.launch {
            val filtered = filterCategoriesByNameUseCase(
                currentState.data.categories.map { it.toCategory() },
                name
            ).map { it.toUiModel() }

            _uiState.value = UiState.Success(
                data = currentState.data.copy(
                    filteredCategories = filtered
                )
            )
        }
    }
}