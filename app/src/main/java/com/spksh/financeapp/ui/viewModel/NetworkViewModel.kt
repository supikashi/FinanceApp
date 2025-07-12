package com.spksh.financeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.connectivity.ConnectivityObserver
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Вьюмодель для отслеживания сети
 */

class NetworkViewModel @Inject constructor(
    observer: ConnectivityObserver
) : ViewModel() {

    private val _status = observer.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ConnectivityObserver.Status.Unavailable)

    val status: StateFlow<ConnectivityObserver.Status> = _status
}