package com.spksh.financeapp.domain.useCase

import jakarta.inject.Inject
import java.time.ZoneId

/**
 * Use-case для получения часового пояса
 */
class GetZoneIdUseCase @Inject constructor() {
    operator fun invoke(): ZoneId {
        return ZoneId.systemDefault()
    }
}