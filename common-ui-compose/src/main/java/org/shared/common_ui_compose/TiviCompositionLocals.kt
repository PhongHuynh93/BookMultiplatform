package org.shared.common_ui_compose

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.common_ui_view.util.TiviTextCreator
import org.test.base_android.util.TiviDateFormatter

val LocalTiviDateFormatter = staticCompositionLocalOf<TiviDateFormatter> {
    error("TiviDateFormatter not provided")
}

val LocalTiviTextCreator = staticCompositionLocalOf<TiviTextCreator> {
    error("TiviTextCreator not provided")
}
