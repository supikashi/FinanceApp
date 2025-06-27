package com.spksh.financeapp.domain.useCase

import jakarta.inject.Inject
import java.time.LocalDate

/**
 * Use-case для получения текущей даты
 */
class GetTodayUseCase @Inject constructor() {
    operator fun invoke(): LocalDate {
        return LocalDate.now()
    }
}