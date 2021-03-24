package com.vb.cache_rest_pagination

import android.app.Application
import com.vb.cache_rest_pagination.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(applicationModule)
        }
    }
}