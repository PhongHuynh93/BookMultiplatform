package com.wind.book

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.SystemLogger
import com.russhwolf.settings.JvmPreferencesSettings
import com.russhwolf.settings.Settings
import com.wind.book.viewmodel.BaseViewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import java.util.prefs.Preferences

actual val platformModule: Module = module {
    single<Settings> {
        JvmPreferencesSettings(Preferences.userRoot())
    }

    val baseKermit = Kermit(SystemLogger()).withTag("Book Multiplatform")
    factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
}

actual inline fun <reified T : BaseViewModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    createdAtStart: Boolean,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> = single(
    qualifier = qualifier,
    createdAtStart = createdAtStart,
    definition = definition
)
