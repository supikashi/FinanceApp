package com.spksh.financeapp.background

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkScheduler {
    fun scheduleDataFetch(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            16,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.Companion.getInstance(context).enqueueUniquePeriodicWork(
            "data_fetch_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}