package com.ineedyourcode.onemockitoplease

import android.app.Application
import com.ineedyourcode.onemockitoplease.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}