package com.example.common_ui_view.extension

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

fun Activity.fullScreen() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Activity.lightStatusBar(lightStatusBar: Boolean) {
    val decorView: View = window.decorView
    ViewCompat.getWindowInsetsController(decorView)?.isAppearanceLightStatusBars = lightStatusBar
}
