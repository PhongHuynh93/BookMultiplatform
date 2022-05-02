package org.test.tivi.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.example.common_ui_view.BaseActivity
import org.shared.common_ui_compose.theme.TiviTheme

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val composeView = ComposeView(this).apply {
            setContent {
                TiviContent()
            }
        }

        // Copied from setContent {} ext-fun
        setOwners()
    }

    @Composable
    private fun TiviContent() {
        CompositionLocalProvider(
            LocalTiviDateFormatter provides tiviDateFormatter,
            LocalTiviTextCreator provides textCreator,
        ) {
            TiviTheme(useDarkColors = preferences.shouldUseDarkColors()) {
                Home(
                    analytics = analytics,
                    onOpenSettings = {
                        // TODO: open setting
//                        startActivity(
//                            Intent(this@MainActivity, SettingsActivity::class.java)
//                        )
                    },
                )
            }
        }
    }
}

private fun ComponentActivity.setOwners() {
    val decorView = window.decorView
    if (ViewTreeLifecycleOwner.get(decorView) == null) {
        ViewTreeLifecycleOwner.set(decorView, this)
    }
    if (ViewTreeViewModelStoreOwner.get(decorView) == null) {
        ViewTreeViewModelStoreOwner.set(decorView, this)
    }
    if (ViewTreeSavedStateRegistryOwner.get(decorView) == null) {
        ViewTreeSavedStateRegistryOwner.set(decorView, this)
    }
}
