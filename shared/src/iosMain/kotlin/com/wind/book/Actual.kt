package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher

actual val platform = PlatformType.IOS

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Main

actual fun runTest(block: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        block()
    }
}

actual fun isDebug() = Platform.isDebugBinary
