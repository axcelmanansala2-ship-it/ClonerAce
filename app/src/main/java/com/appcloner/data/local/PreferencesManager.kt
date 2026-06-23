package com.appcloner.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("cloner_prefs", Context.MODE_PRIVATE)

    var isDarkMode: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) = prefs.edit().putBoolean("dark_mode", value).apply()

    var cloneCount: Int
        get() = prefs.getInt("clone_count", 0)
        set(value) = prefs.edit().putInt("clone_count", value).apply()

    var isFirstLaunch: Boolean
        get() = prefs.getBoolean("first_launch", true)
        set(value) = prefs.edit().putBoolean("first_launch", value).apply()

    var isRootMode: Boolean
        get() = prefs.getBoolean("root_mode", false)
        set(value) = prefs.edit().putBoolean("root_mode", value).apply()
}
