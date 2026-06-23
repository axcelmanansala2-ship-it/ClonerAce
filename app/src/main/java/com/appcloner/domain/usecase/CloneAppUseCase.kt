package com.appcloner.domain.usecase

import android.content.Context
import android.content.pm.PackageManager
import com.appcloner.data.model.ClonedApp
import com.appcloner.data.model.CloneStatus
import com.appcloner.data.repository.CloneRepository
import com.appcloner.service.ApkModifier
import com.appcloner.service.ApkSigner
import com.appcloner.service.RootCommandExecutor
import com.appcloner.utils.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class CloneAppUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cloneRepository: CloneRepository,
    private val apkModifier: ApkModifier,
    private val apkSigner: ApkSigner,
    private val rootCommandExecutor: RootCommandExecutor
) {
    operator fun invoke(packageName: String, cloneName: String): Flow<CloneStatus> = flow {
        try {
            emit(CloneStatus.Progress("Extracting APK...", 10))
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val apkFile = java.io.File(appInfo.sourceDir)

            val cloneId = UUID.randomUUID().toString()
            val clonedPackage = "${packageName}.clone${System.currentTimeMillis()}"

            emit(CloneStatus.Progress("Modifying package...", 30))
            val modifiedApk = apkModifier.modifyPackageName(apkFile, clonedPackage, cloneId)
                ?: throw Exception("Failed to modify APK")

            emit(CloneStatus.Progress("Signing APK...", 60))
            val signedApk = apkSigner.signApk(modifiedApk, cloneId)
                ?: throw Exception("Failed to sign APK")

            emit(CloneStatus.Progress("Installing clone...", 80))
            apkSigner.installApk(signedApk)

            val appName = pm.getApplicationLabel(appInfo).toString()
            val clonedApp = ClonedApp(
                cloneId = cloneId,
                originalPackage = packageName,
                clonedPackage = clonedPackage,
                userHandle = 0,
                appName = cloneName.ifEmpty { "$appName Clone" },
                iconPath = null,
                installDate = System.currentTimeMillis(),
                isActive = true,
                isGameGuardianPatched = false
            )
            cloneRepository.addClonedApp(clonedApp)

            emit(CloneStatus.Progress("Done!", 100))
            emit(CloneStatus.Success(clonedApp))
        } catch (e: Exception) {
            Logger.e("CloneAppUseCase", "Clone failed", e)
            emit(CloneStatus.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
