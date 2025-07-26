package com.spksh.category.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.category.domain.use_case.FilterCategoriesByNameUseCase
import com.spksh.category.ui.state.CategoryScreenState
import com.spksh.domain.useCase.GetCategoriesByTypeFlowUseCase
import com.spksh.domain.useCase.LoadCategoriesFromNetworkUseCase
import com.spksh.ui.model.toUiModel
import com.spksh.ui.state.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Вьюмодель для экрана категорий
 */

class CategoryViewModel @Inject constructor(
    private val getCategoriesByTypeFlowUseCase: GetCategoriesByTypeFlowUseCase,
    private val filterCategoriesByNameUseCase: FilterCategoriesByNameUseCase,
    private val loadCategoriesFromNetworkUseCase: LoadCategoriesFromNetworkUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<CategoryScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<CategoryScreenState>> = _uiState

    private var filterJob: Job? = null

    init {
        Log.i("my_tag", "category viewmodel init")
        viewModelScope.launch {
            getCategoriesByTypeFlowUseCase(false).collect {
                Log.i("my_tag", "category collected")
                if (it.isEmpty()) {
                    fetchData()
                } else {
                    val categories = it.map { it.toUiModel() }
                    _uiState.value = UiState.Success(
                        data = CategoryScreenState(
                            categories = categories,
                            filteredCategories = categories
                        )
                    )
                }
            }
        }
    }

    fun fetchData() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        val response = loadCategoriesFromNetworkUseCase()
        if (response == null) {
            _uiState.value = UiState.Error("")
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