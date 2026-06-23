package com.lody.virtual.client.core

import android.content.Context
import android.content.Intent
import com.lody.virtual.remote.InstallResult

/**
 * Stub — replaced by the real VirtualApp AAR once deps are resolved.
 * All cloning infrastructure is wired correctly; this stub lets the build succeed.
 */
class VirtualCore private constructor() {

    /** Non-null once [startup] succeeds. */
    var context: Context? = null
        private set

    @Throws(Throwable::class)
    fun startup(ctx: Context) {
        context = ctx
        // Real impl: hooks Android framework to create a virtual process space.
        // Pending VirtualApp AAR integration (Bintray deps -> local Maven build needed).
    }

    fun installPackage(apkPath: String, flags: Int): InstallResult =
        InstallResult.notReady()

    fun installPackageAsUser(userId: Int, packageName: String): Boolean = false

    fun uninstallPackageAsUser(packageName: String, userId: Int): Boolean = false

    fun clearPackageAsUser(userId: Int, packageName: String): Boolean = false

    fun isAppInstalled(packageName: String): Boolean = false

    fun isAppRunning(packageName: String, userId: Int): Boolean = false

    fun getLaunchIntent(packageName: String, userId: Int): Intent? = null

    companion object {
        @JvmStatic
        fun get(): VirtualCore = instance
        private val instance = VirtualCore()
    }
}
