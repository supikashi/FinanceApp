package com.spksh.financeapp.domain.useCase

import jakarta.inject.Inject
import java.time.ZoneId

class GetZoneIdUseCase @Inject constructor() {
    operator fun invoke(): ZoneId {
        return ZoneId.systemDefault()
    }
}