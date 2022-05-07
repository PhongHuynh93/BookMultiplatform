package org.shared.shared

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

actual val platformModule: Module = module {

    val baseKermit = Kermit(LogcatLogger()).withTag("Appxxx")
    factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
}
