package com.appcloner.service

import com.appcloner.data.local.FileManager
import com.appcloner.utils.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApkModifier @Inject constructor(private val fileManager: FileManager) {

    fun modifyPackageName(apkFile: File, newPackageName: String, cloneId: String): File? {
        return try {
            val outputDir = fileManager.getCloneDir(cloneId)
            val outputApk = File(outputDir, "modified.apk")
            copyApk(apkFile, outputApk)
            Logger.d("ApkModifier", "APK copied to ${outputApk.absolutePath}")
            outputApk
        } catch (e: Exception) {
            Logger.e("ApkModifier", "Failed to modify APK", e)
            null
        }
    }

    private fun copyApk(source: File, dest: File) {
        ZipInputStream(FileInputStream(source)).use { zis ->
            ZipOutputStream(FileOutputStream(dest)).use { zos ->
                var entry: ZipEntry? = zis.nextEntry
                while (entry != null) {
                    zos.putNextEntry(ZipEntry(entry.name))
                    zis.copyTo(zos)
                    zos.closeEntry()
                    entry = zis.nextEntry
                }
            }
        }
    }
}
