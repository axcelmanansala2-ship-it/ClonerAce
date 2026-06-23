package com.appcloner.admin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import com.appcloner.utils.Logger

class DeviceAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Logger.d("DeviceAdminReceiver", "Device admin enabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Logger.d("DeviceAdminReceiver", "Device admin disabled")
    }
}
