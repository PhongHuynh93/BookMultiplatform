package com.wind.book.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

@SuppressLint("SetJavaScriptEnabled")
class CocaWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : WebView(context, attrs, defStyle) {
    init {
        // speed up loading https://stackoverflow.com/questions/7422427/android-webview-slow
        // chromium, enable hardware acceleration
        setLayerType(LAYER_TYPE_HARDWARE, null)

        val settings = settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.displayZoomControls = false
        settings.domStorageEnabled = true
    }
}
