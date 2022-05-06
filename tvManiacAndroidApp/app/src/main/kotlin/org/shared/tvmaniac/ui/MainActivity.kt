package org.shared.tvmaniac.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import org.shared.compose.theme.TvManiacTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
//                TvManiacTheme(darkTheme = themePreference.shouldUseDarkColors()) {
//                    SetupTheme()
//                    HomeScreen(composeNavigationFactories)
//                }
            }

//            ConnectivityStatus(observeNetwork)
        }
    }
}
