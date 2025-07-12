package com.spksh.domain.useCase

import java.time.ZoneId

/**
 * Use-case для получения часового пояса
 */
class GetZoneIdUseCase() {
    operator fun invoke(): ZoneId {
        return ZoneId.systemDefault()
    }
}