@file:Suppress("DEPRECATION")

package org.test.tivi

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.wind.book.AppInfo
import com.wind.book.initKoin
import com.wind.book.util.AppContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.test.base.settings.TiviPreferences
import org.test.base.util.PowerController
import org.test.tivi.util.AndroidPowerController
import org.test.tivi_ui_settings.TiviPreferencesImpl

class TiviApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<AppContext> { this@TiviApplication }
                single<Context> { this@TiviApplication }
                single<AppInfo> { AndroidAppInfo }
                single<PowerController> { AndroidPowerController(get(), get()) }
                single<SharedPreferences>(named("app")) {
                    PreferenceManager.getDefaultSharedPreferences(
                        this@TiviApplication
                    )
                }
                single<TiviPreferences> { TiviPreferencesImpl(get(), get(named("app"))) }
            },
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
