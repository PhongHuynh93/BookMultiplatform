package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val platform = PlatformType.JVM

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Default

// FIXME: JVM don't have this field
actual fun isDebug() = false
