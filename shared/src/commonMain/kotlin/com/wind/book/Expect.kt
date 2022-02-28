package com.wind.book

import com.wind.book.model.PlatformType
import kotlinx.coroutines.CoroutineDispatcher

expect val platform: PlatformType

expect val platformCoroutineDispatcher: CoroutineDispatcher

expect fun isDebug(): Boolean

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class Parcelize()

expect interface Parcelable
