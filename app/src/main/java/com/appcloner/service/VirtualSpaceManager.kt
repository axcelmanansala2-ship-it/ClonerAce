package com.appcloner.service

import android.content.Context
import com.appcloner.utils.Logger
import com.lody.virtual.client.core.VirtualCore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Thin facade over VirtualApp2022's VirtualCore engine.
 */
@Singleton
class VirtualSpaceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /** True once VirtualCore.startup() has succeeded (checked via its public getContext()). */
    val isEngineReady: Boolean
        get() = try {
            VirtualCore.get().context != null
        } catch (e: Throwable) { false }

    fun isAppInstalledVirtually(packageName: String): Boolean =
        try { VirtualCore.get().isAppInstalled(packageName) } catch (e: Throwable) { false }

    fun isAppRunning(packageName: String, userId: Int): Boolean =
        try { VirtualCore.get().isAppRunning(packageName, userId) } catch (e: Throwable) { false }

    fun clearAppData(packageName: String, userId: Int): Boolean =
        try { VirtualCore.get().clearPackageAsUser(userId, packageName) } catch (e: Throwable) { false }
}
