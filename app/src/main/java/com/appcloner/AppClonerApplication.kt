package com.appcloner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClonerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
