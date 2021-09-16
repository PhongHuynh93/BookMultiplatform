package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher

expect val platform: PlatformType

expect val platformCoroutineDispatcher: CoroutineDispatcher

expect fun runTest(block: suspend () -> Unit)

expect fun isDebug(): Boolean
