package com.appcloner.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.io.File

class CleanupWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val clonerDir = File(applicationContext.filesDir, "clones")
            clonerDir.listFiles()?.forEach { dir ->
                val modified = File(dir, "modified.apk")
                if (modified.exists()) modified.delete()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
