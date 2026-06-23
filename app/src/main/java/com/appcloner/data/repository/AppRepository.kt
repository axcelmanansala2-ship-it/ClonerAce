package com.appcloner.data.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.appcloner.data.model.AppInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val context: Context) {

    fun getInstalledApps(): List<AppInfo> {
        val pm = context.packageManager
        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { isUserApp(it) }
            .map { appInfo ->
                val pkgInfo = try { pm.getPackageInfo(appInfo.packageName, 0) } catch (e: Exception) { null }
                AppInfo(
                    packageName = appInfo.packageName,
                    appName = pm.getApplicationLabel(appInfo).toString(),
                    icon = try { pm.getApplicationIcon(appInfo.packageName) } catch (e: Exception) { null },
                    apkPath = appInfo.sourceDir,
                    sizeBytes = try { java.io.File(appInfo.sourceDir).length() } catch (e: Exception) { 0L },
                    installDate = pkgInfo?.firstInstallTime ?: 0L
                )
            }.sortedBy { it.appName }
    }

    private fun isUserApp(appInfo: ApplicationInfo): Boolean {
        return (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
    }
}
