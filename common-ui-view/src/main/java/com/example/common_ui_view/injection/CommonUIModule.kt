package com.example.common_ui_view.injection

import com.example.common_ui_view.util.TiviTextCreator
import org.koin.dsl.module

val commonUIModule = module {
    factory<TiviTextCreator> {
        TiviTextCreator(get(), get())
    }
}
