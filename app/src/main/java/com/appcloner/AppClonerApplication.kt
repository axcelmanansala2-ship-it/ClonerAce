package com.appcloner

import android.app.Application
import com.lody.virtual.client.core.VirtualCore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClonerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initVirtualEngine()
    }

    private fun initVirtualEngine() {
        try {
            // Must run on main thread; initialises VirtualApp2022's binder hooks
            VirtualCore.get().startup(this)
        } catch (e: Throwable) {
            // Falls back to legacy APK-install mode if engine fails
        }
    }
}
