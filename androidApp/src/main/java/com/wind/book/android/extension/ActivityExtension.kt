package com.wind.book.android.extension

import android.app.Activity
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.fullScreen() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Activity.lightStatusBar(lightStatusBar: Boolean) {
    val decorView: View = window.decorView
    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = lightStatusBar
}
