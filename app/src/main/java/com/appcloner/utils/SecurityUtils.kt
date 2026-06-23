package com.appcloner.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import java.security.MessageDigest

object SecurityUtils {
    fun getAppSignature(context: Context, packageName: String): String? {
        return try {
            val info = context.packageManager.getPackageInfo(
                packageName, PackageManager.GET_SIGNATURES
            )
            val sig: Signature = info.signatures[0]
            val md = MessageDigest.getInstance("SHA")
            md.update(sig.toByteArray())
            md.digest().joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun isCriticalSystemApp(packageName: String): Boolean {
        val critical = setOf(
            "android", "com.android.systemui", "com.android.settings",
            "com.android.phone", "com.google.android.gms"
        )
        return packageName in critical
    }
}
