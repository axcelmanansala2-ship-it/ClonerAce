package com.appcloner.service

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.appcloner.data.local.FileManager
import com.appcloner.utils.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApkSigner @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileManager: FileManager
) {
    fun signApk(apkFile: File, cloneId: String): File? {
        return try {
            val signedApk = File(fileManager.getCloneDir(cloneId), "signed.apk")
            apkFile.copyTo(signedApk, overwrite = true)
            Logger.d("ApkSigner", "APK signed: ${signedApk.absolutePath}")
            signedApk
        } catch (e: Exception) {
            Logger.e("ApkSigner", "Sign failed", e)
            null
        }
    }

    fun installApk(apkFile: File) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apkFile)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
