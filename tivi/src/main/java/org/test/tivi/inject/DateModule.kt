package org.test.tivi.inject

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

internal val dateModule = module {
    single<DateTimeFormatter>(named("ShortTime")) {
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(get())
    }
    single<DateTimeFormatter>(named("ShortDate")) {
        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(get())
    }
    single<DateTimeFormatter>(named("MediumDate")) {
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(get())
    }
    single<DateTimeFormatter>(named("MediumDateTime")) {
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(get())
    }
}
