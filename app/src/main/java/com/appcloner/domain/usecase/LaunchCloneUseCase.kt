package com.appcloner.domain.usecase

import android.content.Context
import android.content.Intent
import com.appcloner.service.RootCommandExecutor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LaunchCloneUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val rootCommandExecutor: RootCommandExecutor
) {
    fun invoke(clonedPackage: String, userHandle: Int): Boolean {
        return try {
            if (rootCommandExecutor.isRootAvailable()) {
                rootCommandExecutor.execute("am start -n $clonedPackage/.MainActivity --user $userHandle")
                true
            } else {
                val intent = context.packageManager.getLaunchIntentForPackage(clonedPackage)
                intent?.let {
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(it)
                    true
                } ?: false
            }
        } catch (e: Exception) {
            false
        }
    }
}
