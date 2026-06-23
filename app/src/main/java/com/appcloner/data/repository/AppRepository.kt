package com.appcloner.data.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.appcloner.data.model.AppInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getInstalledApps(includeSystemApps: Boolean = false): Flow<List<AppInfo>> = flow {
        val pm = context.packageManager
        val flags = PackageManager.GET_META_DATA
        val packages = pm.getInstalledApplications(flags)
        val apps = packages
            .filter { info ->
                includeSystemApps || (info.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            }
            .map { info ->
                val pkgInfo = try { pm.getPackageInfo(info.packageName, 0) } catch (e: Exception) { null }
                AppInfo(
                    packageName = info.packageName,
                    appName = pm.getApplicationLabel(info).toString(),
                    icon = try { pm.getApplicationIcon(info.packageName) } catch (e: Exception) { null },
                    apkPath = info.sourceDir ?: "",
                    sizeBytes = try { java.io.File(info.sourceDir).length() } catch (e: Exception) { 0L },
                    installDate = pkgInfo?.firstInstallTime ?: 0L
                )
            }
            .sortedBy { it.appName }
        emit(apps)
    }.flowOn(Dispatchers.IO)
}
