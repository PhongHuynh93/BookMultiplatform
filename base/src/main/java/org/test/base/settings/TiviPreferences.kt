package org.test.base.settings

import kotlinx.coroutines.flow.Flow

interface TiviPreferences {

    fun setup()

    var theme: Theme
    fun observeTheme(): Flow<Theme>

    var useLessData: Boolean
    fun observeUseLessData(): Flow<Boolean>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }
}
