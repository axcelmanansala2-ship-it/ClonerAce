package com.appcloner.service

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.appcloner.admin.DeviceAdminReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VirtualSpaceManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val rootCommandExecutor: RootCommandExecutor
) {
    private val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private val adminComponent = ComponentName(context, DeviceAdminReceiver::class.java)

    fun createVirtualSpace(rootMode: Boolean): Int {
        return if (rootMode && rootCommandExecutor.isRootAvailable()) {
            createRootVirtualSpace()
        } else {
            createWorkProfileVirtualSpace()
        }
    }

    private fun createRootVirtualSpace(): Int {
        rootCommandExecutor.execute("pm create-user --managed ClonerAce_Space")
        return 0
    }

    private fun createWorkProfileVirtualSpace(): Int {
        return 0
    }

    fun isDeviceAdminActive(): Boolean =
        dpm.isAdminActive(adminComponent)
}
