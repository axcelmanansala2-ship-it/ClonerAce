package com.appcloner.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.appcloner.data.model.ClonedApp
import com.appcloner.data.repository.CloneRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UninstallCloneUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cloneRepository: CloneRepository
) {
    suspend fun invoke(clonedApp: ClonedApp): Boolean {
        return try {
            cloneRepository.removeClonedApp(clonedApp)
            val intent = Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:${clonedApp.clonedPackage}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }
}
