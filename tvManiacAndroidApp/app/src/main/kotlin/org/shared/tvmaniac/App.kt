package org.shared.tvmaniac

import android.app.Application
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
//                single<AppContext> { this@App }
            },
        )
    }
}
