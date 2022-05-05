package org.test.base_android.injection

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.test.base_android.util.TiviDateFormatter

val baseAndroidModule = module {
    single<TiviDateFormatter> {
        TiviDateFormatter(
            get(named("ShortTime")),
            get(named("ShortDate")),
            get(named("MediumDate")),
            get(named("MediumDateTime")),
        )
    }
}
