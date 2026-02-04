package com.project.fitify

import android.app.Application
import com.project.fitify.common.di.appModule
import com.project.fitify.common.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@App)
            modules(networkModule, appModule)
        }
    }
}