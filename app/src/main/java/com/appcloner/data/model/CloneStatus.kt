package com.appcloner.data.model

sealed class CloneStatus {
    object Idle : CloneStatus()
    data class Progress(val step: String, val percent: Int) : CloneStatus()
    data class Success(val clonedApp: ClonedApp) : CloneStatus()
    data class Error(val message: String) : CloneStatus()
}
