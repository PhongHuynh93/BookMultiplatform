package org.shared.util

import org.koin.dsl.module
import org.shared.util.network.ObserveConnectionState

val coreUtilModule = module {
    single {
        ObserveConnectionState(get())
    }
}
