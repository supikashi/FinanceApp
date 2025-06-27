package com.spksh.financeapp.ui.features

import kotlinx.coroutines.delay
import retrofit2.HttpException

/**
 * При коде ошибки 500 делает 3 перезапроса в сеть
 */
suspend fun multipleFetch(
    fetch: suspend () -> Unit
) {
    val maxAttempts = 3
    val retryDelayMs = 2_000L
    var lastError: Throwable? = null

    for (attempt in 1..maxAttempts) {
        try {
            fetch()
            return
        } catch (e: Throwable) {
            lastError = e
            val isServerError = (e is HttpException && e.code() == 500)
            if (isServerError && attempt < maxAttempts) {
                delay(retryDelayMs)
                continue
            }
            throw e
        }
    }
    throw lastError ?: Throwable()
}