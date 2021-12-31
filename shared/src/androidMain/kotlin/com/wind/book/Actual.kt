package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val platform = PlatformType.ANDROID

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

actual fun isDebug() = BuildConfig.DEBUG
