package org.shared.tvmaniac

import android.app.Application
import org.koin.dsl.module
import org.shared.shared.initKoin
import org.shared.util.AppContext
import org.shared.util.coreUtilModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<AppContext> { this@App }
            },
            coreUtilModule
        )
    }
}
