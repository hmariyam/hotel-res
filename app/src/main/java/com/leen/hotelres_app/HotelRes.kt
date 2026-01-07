package com.leen.hotelres_app

import android.app.Application
import com.leen.hotelres_app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HotelRes : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HotelRes)
            modules(appModule)
        }
    }
}