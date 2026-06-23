package com.appcloner.domain.usecase

import com.appcloner.data.model.AppInfo
import com.appcloner.data.repository.AppRepository
import com.appcloner.data.repository.CloneRepository
import com.appcloner.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetInstalledAppsUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val cloneRepository: CloneRepository
) {
    suspend operator fun invoke(): Result<List<AppInfo>> = withContext(Dispatchers.IO) {
        try {
            val appList = appRepository.getInstalledApps().first()
            val apps = appList.map { app ->
                app.copy(isCloned = cloneRepository.isCloned(app.packageName))
            }
            Result.Success(apps)
        } catch (e: Exception) {
            Result.Error("Failed to load apps: ${e.message}", e)
        }
    }
}
