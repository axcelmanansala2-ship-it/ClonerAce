package com.appcloner.domain.usecase

import com.appcloner.data.model.ClonedApp
import com.appcloner.data.repository.CloneRepository
import com.appcloner.utils.Logger
import com.lody.virtual.client.core.VirtualCore
import javax.inject.Inject

class UninstallCloneUseCase @Inject constructor(
    private val cloneRepository: CloneRepository
) {
    suspend fun invoke(clonedApp: ClonedApp): Boolean {
        return try {
            VirtualCore.get().uninstallPackageAsUser(
                clonedApp.originalPackage,
                clonedApp.userHandle
            )
            cloneRepository.removeClonedApp(clonedApp)
            true
        } catch (e: Exception) {
            Logger.e("UninstallClone", "Uninstall failed", e)
            cloneRepository.removeClonedApp(clonedApp)
            false
        }
    }
}
