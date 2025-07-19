package com.spksh.financeapp

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.spksh.financeapp.background.SyncWorkerFactory
import com.spksh.financeapp.di.AppComponent
import com.spksh.financeapp.di.DaggerAppComponent
import javax.inject.Inject


class FinanceApp : Application() {

    lateinit var appComponent: AppComponent
    @Inject
    lateinit var syncWorkerFactory: SyncWorkerFactory

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.injectTo(this)
        super.onCreate()
        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(syncWorkerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is FinanceApp -> this.appComponent
        else -> (this.applicationContext as FinanceApp).appComponent
    }
