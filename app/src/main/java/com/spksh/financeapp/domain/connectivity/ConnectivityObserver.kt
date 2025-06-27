package com.spksh.financeapp.domain.connectivity

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для наблюдения за сетевым подключением устройства
 */
interface ConnectivityObserver {
    enum class Status { Available, Unavailable, Losing, Lost }
    fun observe(): Flow<Status>
}