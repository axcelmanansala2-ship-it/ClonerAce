package com.appcloner.domain.usecase

import android.content.Context
import android.content.Intent
import com.appcloner.utils.Logger
import com.lody.virtual.client.core.VirtualCore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LaunchCloneUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun invoke(originalPackage: String, userHandle: Int): Boolean {
        return try {
            // getLaunchIntent returns an Intent that VirtualApp routes into the virtual space
            val intent = VirtualCore.get().getLaunchIntent(originalPackage, userHandle)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                true
            } else {
                Logger.e("LaunchClone", "No launch intent for $originalPackage user=$userHandle")
                false
            }
        } catch (e: Exception) {
            Logger.e("LaunchClone", "Launch failed", e)
            false
        }
    }
}
