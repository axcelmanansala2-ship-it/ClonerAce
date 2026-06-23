package com.appcloner.domain.usecase

import android.content.Context
import com.appcloner.data.model.CloneStatus
import com.appcloner.data.model.ClonedApp
import com.appcloner.data.repository.CloneRepository
import com.appcloner.utils.Logger
import com.lody.virtual.client.core.InstallStrategy
import com.lody.virtual.client.core.VirtualCore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class CloneAppUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cloneRepository: CloneRepository
) {
    operator fun invoke(packageName: String, cloneName: String): Flow<CloneStatus> = flow {
        try {
            emit(CloneStatus.Progress("Preparing virtual space...", 10))

            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val apkPath = appInfo.sourceDir
            val appLabel = pm.getApplicationLabel(appInfo).toString()

            emit(CloneStatus.Progress("Installing into virtual space...", 40))

            // Install APK into VirtualApp2022 virtual engine (no user-facing install dialog)
            val result = VirtualCore.get().installPackage(apkPath, InstallStrategy.UPDATE_IF_EXIST)
            if (!result.isSuccess) {
                throw Exception("Virtual install failed: " + result.error)
            }

            emit(CloneStatus.Progress("Assigning virtual user slot...", 70))

            // Get next free slot so multiple clones of the same app can coexist
            val userSlot = cloneRepository.getNextUserSlot(packageName)
            VirtualCore.get().installPackageAsUser(userSlot, packageName)

            val cloneId = UUID.randomUUID().toString()
            val clonedApp = ClonedApp(
                cloneId    = cloneId,
                originalPackage = packageName,
                clonedPackage   = packageName,   // same package name, isolated by virtual user
                userHandle = userSlot,
                appName    = cloneName.ifEmpty { "$appLabel Clone" },
                iconPath   = null,
                installDate = System.currentTimeMillis(),
                isActive   = true,
                isGameGuardianPatched = false
            )
            cloneRepository.addClonedApp(clonedApp)

            emit(CloneStatus.Progress("Done!", 100))
            emit(CloneStatus.Success(clonedApp))

        } catch (e: Exception) {
            Logger.e("CloneAppUseCase", "Virtual clone failed", e)
            emit(CloneStatus.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
