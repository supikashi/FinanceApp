package com.spksh.financeapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.connectivity.ConnectivityObserver
import com.spksh.domain.useCase.SynchronizeUpdatedUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Вьюмодель для отслеживания сети
 */

class NetworkViewModel @Inject constructor(
    observer: ConnectivityObserver,
    private val synchronizeUpdatedUseCase: SynchronizeUpdatedUseCase
) : ViewModel() {

    private val _status = observer.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ConnectivityObserver.Status.Unavailable)

    val status: StateFlow<ConnectivityObserver.Status> = _status

    init {
        _status.onEach { status ->
            Log.i("my_tag", "collect network")
            if (status == ConnectivityObserver.Status.Available) {
                try {
                    synchronizeUpdatedUseCase()
                } catch (e: Exception) {
                    Log.i("my_tag", "collect network fail ${e.message}")
                }
            }
        }.launchIn(viewModelScope)
    }
}