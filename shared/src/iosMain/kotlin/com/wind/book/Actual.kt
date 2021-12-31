package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val platform = PlatformType.IOS

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun isDebug() = Platform.isDebugBinary
