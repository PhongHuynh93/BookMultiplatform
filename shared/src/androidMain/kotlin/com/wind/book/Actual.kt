package com.wind.book

import android.os.Parcelable
import com.wind.book.model.PlatformType
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val platform = PlatformType.ANDROID

actual val platformCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

actual fun isDebug() = BuildConfig.DEBUG

actual typealias Parcelize = Parcelize

actual typealias Parcelable = Parcelable
