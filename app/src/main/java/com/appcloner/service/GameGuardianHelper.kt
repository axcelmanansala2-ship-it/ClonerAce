package com.appcloner.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameGuardianHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val rootCommandExecutor: RootCommandExecutor
) {
    companion object {
        private const val GG_PACKAGE = "catch_.me_.if_.you_.can_"
        private const val GG_DOWNLOAD_URL = "https://gameguardian.net/download"
    }

    fun isGameGuardianInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo(GG_PACKAGE, 0)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun launchGameGuardian() {
        val intent = context.packageManager.getLaunchIntentForPackage(GG_PACKAGE)
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

    fun openGGDownloadPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GG_DOWNLOAD_URL))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun patchForGameGuardian(packageName: String): Boolean {
        if (!rootCommandExecutor.isRootAvailable()) return false
        rootCommandExecutor.execute("setprop ro.debuggable 1")
        return true
    }

    fun attachGameGuardian(targetPid: Int): Boolean {
        if (!isGameGuardianInstalled()) return false
        return rootCommandExecutor.execute("am startservice $GG_PACKAGE/.GGService --ei pid $targetPid")
    }
}
