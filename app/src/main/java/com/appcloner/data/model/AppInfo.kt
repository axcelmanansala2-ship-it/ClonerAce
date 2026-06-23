package com.appcloner.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    val apkPath: String,
    val sizeBytes: Long,
    val installDate: Long,
    val isCloned: Boolean = false
)
