package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher

expect val platform: PlatformType

expect val platformCoroutineDispatcher: CoroutineDispatcher

expect fun isDebug(): Boolean
