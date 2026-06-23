package com.appcloner.worker

import android.content.Context
import androidx.work.*

class CloneWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val packageName = inputData.getString("package_name") ?: return Result.failure()
        val cloneName = inputData.getString("clone_name") ?: ""
        return try {
            Result.success(workDataOf("package_name" to packageName, "clone_name" to cloneName))
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        fun buildRequest(packageName: String, cloneName: String): OneTimeWorkRequest {
            val inputData = workDataOf("package_name" to packageName, "clone_name" to cloneName)
            return OneTimeWorkRequestBuilder<CloneWorker>()
                .setInputData(inputData)
                .build()
        }
    }
}
