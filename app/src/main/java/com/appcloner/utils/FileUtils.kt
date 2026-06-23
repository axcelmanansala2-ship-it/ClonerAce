package com.appcloner.utils

import java.io.File

object FileUtils {
    fun copyFile(source: File, dest: File): Boolean {
        return try {
            source.copyTo(dest, overwrite = true)
            true
        } catch (e: Exception) {
            Logger.e("FileUtils", "Copy failed: ${e.message}")
            false
        }
    }

    fun deleteRecursively(file: File): Boolean =
        file.deleteRecursively()

    fun getFileSize(file: File): Long =
        if (file.exists()) file.length() else 0L

    fun ensureDir(dir: File): Boolean {
        if (!dir.exists()) return dir.mkdirs()
        return dir.isDirectory
    }
}
