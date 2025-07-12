package com.spksh.domain.useCase

import java.time.LocalDate

/**
 * Use-case для получения текущей даты
 */
class GetTodayUseCase() {
    operator fun invoke(): LocalDate {
        return LocalDate.now()
    }
}