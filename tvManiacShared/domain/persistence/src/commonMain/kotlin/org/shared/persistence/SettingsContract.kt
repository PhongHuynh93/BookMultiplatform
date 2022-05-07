package org.shared.persistence

import org.shared.ui.Action
import org.shared.ui.Effect
import org.shared.ui.State

data class SettingsState(
    val theme: Theme,
    val showPopup: Boolean
) : State {
    companion object {
        val DEFAULT = SettingsState(
            theme = Theme.SYSTEM,
            showPopup = false
        )
    }
}

sealed class SettingsActions : Action {
    data class ThemeSelected(
        val theme: String
    ) : SettingsActions()

    object LoadTheme : SettingsActions()
    object ThemeClicked : SettingsActions()
}

sealed class SettingsEffect : Effect

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM
}
