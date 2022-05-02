package org.shared.common_ui_compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TiviTheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (useDarkColors) TiviDarkColors else TiviLightColors,
        typography = TiviTypography,
        shapes = TiviShapes,
        content = content
    )
}
