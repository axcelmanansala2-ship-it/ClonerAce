package com.appcloner.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cloned_apps")
data class ClonedApp(
    @PrimaryKey val cloneId: String,
    val originalPackage: String,
    val clonedPackage: String,
    val userHandle: Int,
    val appName: String,
    val iconPath: String?,
    val installDate: Long,
    val isActive: Boolean,
    val isGameGuardianPatched: Boolean
)
