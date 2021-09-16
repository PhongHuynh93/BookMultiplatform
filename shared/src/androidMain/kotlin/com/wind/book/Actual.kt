package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

actual val platform = PlatformType.ANDROID

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

actual fun runTest(block: suspend () -> Unit) = runBlocking { block() }

actual fun isDebug() = BuildConfig.DEBUG
