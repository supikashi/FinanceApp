package com.spksh.financeapp.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.spksh.domain.useCase.SynchronizeDatabaseUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SyncWorker @AssistedInject constructor(
    @Assisted private val fooContext: Context,
    @Assisted private val params: WorkerParameters,
    private val synchronizeDatabaseUseCase: SynchronizeDatabaseUseCase
) : CoroutineWorker(fooContext, params) {
    private val tag = "my_tag"
    override suspend fun doWork(): Result {
        Log.i(tag, "worker do work")
        synchronizeDatabaseUseCase()
        return Result.success()
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): SyncWorker
    }
}