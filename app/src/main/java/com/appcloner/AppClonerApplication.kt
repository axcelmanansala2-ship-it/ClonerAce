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
            VirtualCore.get().startup(this)
        } catch (e: Throwable) {
            // Stub startup — no-op until real VirtualApp AAR is linked
        }
    }
}
