package com.spksh.financeapp

import android.app.Application
import android.content.Context
import com.spksh.financeapp.di.AppComponent
import com.spksh.financeapp.di.DaggerAppComponent


class FinanceApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is FinanceApp -> this.appComponent
        else -> (this.applicationContext as FinanceApp).appComponent
    }
