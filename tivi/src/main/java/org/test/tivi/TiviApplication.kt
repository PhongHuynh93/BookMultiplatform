package org.test.tivi

import android.app.Application
import com.wind.book.AppInfo
import com.wind.book.initKoin
import com.wind.book.util.AppContext
import org.koin.dsl.module

class TiviApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<AppContext> { this@TiviApplication }
                single<AppInfo> { AndroidAppInfo }
            },
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
