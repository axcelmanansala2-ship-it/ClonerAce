package com.appcloner.utils

import android.util.Log

object Logger {
    private const val APP_TAG = "ClonerAce"
    var isDebug = true

    fun d(tag: String, message: String) {
        if (isDebug) Log.d("$APP_TAG/$tag", message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Log.e("$APP_TAG/$tag", message, throwable)
    }

    fun i(tag: String, message: String) {
        Log.i("$APP_TAG/$tag", message)
    }

    fun w(tag: String, message: String) {
        Log.w("$APP_TAG/$tag", message)
    }
}
