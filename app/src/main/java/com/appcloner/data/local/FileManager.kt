package com.appcloner.data.local

import android.content.Context
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManager @Inject constructor(private val context: Context) {

    val clonerBaseDir: File get() = File(context.filesDir, "clones").also { it.mkdirs() }

    fun getCloneDir(cloneId: String): File =
        File(clonerBaseDir, cloneId).also { it.mkdirs() }

    fun getApkFile(cloneId: String): File =
        File(getCloneDir(cloneId), "clone.apk")

    fun getIconFile(cloneId: String): File =
        File(getCloneDir(cloneId), "icon.png")

    fun deleteCloneDir(cloneId: String): Boolean =
        getCloneDir(cloneId).deleteRecursively()

    fun getAvailableSpaceBytes(): Long =
        context.filesDir.freeSpace
}
