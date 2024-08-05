package com.samueljuma.superstocks

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SuperStocksApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@SuperStocksApplication)
            modules(appModule)
        }
    }
}