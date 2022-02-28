package com.wind.book.android

import android.app.Application
import android.content.Context
import com.wind.book.AppInfo
import com.wind.book.initKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@App }
                single<AppInfo> { AndroidAppInfo }
            },
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
