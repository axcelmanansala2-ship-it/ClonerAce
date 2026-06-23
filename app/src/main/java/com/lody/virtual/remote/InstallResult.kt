package com.lody.virtual.remote

data class InstallResult(
    val isSuccess: Boolean,
    val packageName: String?,
    val error: String?
) {
    companion object {
        fun success(pkg: String) = InstallResult(true, pkg, null)
        fun failed(reason: String) = InstallResult(false, null, reason)
        fun notReady() = InstallResult(false, null, "VirtualApp engine not loaded — add virtualapp.aar to app/libs/")
    }
}
