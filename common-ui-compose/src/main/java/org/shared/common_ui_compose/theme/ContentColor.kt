@file:Suppress("NOTHING_TO_INLINE")

package org.shared.common_ui_compose.theme

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Returns the [LocalContentColor] with the current [LocalContentAlpha].
 */
@Composable
inline fun foregroundColor(): Color = LocalContentColor.current.copy(LocalContentAlpha.current)
