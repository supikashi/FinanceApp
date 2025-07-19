package com.spksh.financeapp.background

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor(
    private val syncWorkerFactory: SyncWorker.Factory,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name ->
                syncWorkerFactory.create(appContext, workerParameters)
            else -> null
        }
    }
}