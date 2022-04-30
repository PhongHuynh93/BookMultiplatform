package org.test.tivi_ui_settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.test.base.settings.TiviPreferences

class TiviPreferencesImpl constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : TiviPreferences {
    private val defaultThemeValue = context.getString(R.string.pref_theme_default_value)

    private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        preferenceKeyChangedFlow.tryEmit(key)
    }

    companion object {
        const val KEY_THEME = "pref_theme"
        const val KEY_DATA_SAVER = "pref_data_saver"
    }

    override fun setup() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override var theme: TiviPreferences.Theme
        get() = getThemeForStorageValue(sharedPreferences.getString(KEY_THEME, defaultThemeValue)!!)
        set(value) = sharedPreferences.edit {
            putString(KEY_THEME, value.storageKey)
        }

    override var useLessData: Boolean
        get() = sharedPreferences.getBoolean(KEY_DATA_SAVER, false)
        set(value) = sharedPreferences.edit {
            putBoolean(KEY_DATA_SAVER, value)
        }

    override fun observeUseLessData(): Flow<Boolean> {
        return preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(KEY_DATA_SAVER) }
            .filter { it == KEY_DATA_SAVER }
            .map { useLessData }
            .distinctUntilChanged()
    }

    override fun observeTheme(): Flow<TiviPreferences.Theme> {
        return preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(KEY_THEME) }
            .filter { it == KEY_THEME }
            .map { theme }
            .distinctUntilChanged()
    }

    private val TiviPreferences.Theme.storageKey: String
        get() = when (this) {
            TiviPreferences.Theme.LIGHT -> context.getString(R.string.pref_theme_light_value)
            TiviPreferences.Theme.DARK -> context.getString(R.string.pref_theme_dark_value)
            TiviPreferences.Theme.SYSTEM -> context.getString(R.string.pref_theme_system_value)
        }

    private fun getThemeForStorageValue(value: String) = when (value) {
        context.getString(R.string.pref_theme_light_value) -> TiviPreferences.Theme.LIGHT
        context.getString(R.string.pref_theme_dark_value) -> TiviPreferences.Theme.DARK
        else -> TiviPreferences.Theme.SYSTEM
    }
}
