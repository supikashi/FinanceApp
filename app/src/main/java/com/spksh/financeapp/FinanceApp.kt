package com.spksh.financeapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.spksh.domain.repository.DataStoreRepository
import com.spksh.financeapp.background.SyncWorkerFactory
import com.spksh.financeapp.di.AppComponent
import com.spksh.financeapp.di.DaggerAppComponent
import java.util.Locale
import javax.inject.Inject
import kotlin.toString


class FinanceApp : Application() {

    lateinit var appComponent: AppComponent
    @Inject
    lateinit var syncWorkerFactory: SyncWorkerFactory
    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.injectToApplication(this)
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

val Context.dataStoreRepository: DataStoreRepository
    get() = when(this) {
        is FinanceApp -> this.dataStoreRepository
        else -> (this.applicationContext as FinanceApp).dataStoreRepository
    }
