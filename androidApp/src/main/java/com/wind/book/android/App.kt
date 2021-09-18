package com.wind.book.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.wind.book.AppInfo
import com.wind.book.initKoin
import com.wind.book.viewmodel.home.BookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@App }
                single<SharedPreferences> {
                    get<Context>().getSharedPreferences("BOOK_SETTINGS", MODE_PRIVATE)
                }
                single<AppInfo> { AndroidAppInfo }
                viewModel { BookViewModel(get()) }
            },
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}