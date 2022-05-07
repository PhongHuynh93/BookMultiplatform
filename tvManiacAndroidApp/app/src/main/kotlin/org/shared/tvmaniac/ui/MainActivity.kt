package org.shared.tvmaniac.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.get
import org.shared.compose.components.ConnectionStatus
import org.shared.compose.theme.DarkColors
import org.shared.compose.theme.LightColors
import org.shared.compose.theme.TvManiacTheme
import org.shared.util.network.ConnectionState
import org.shared.util.network.ObserveConnectionState

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                TvManiacTheme() {
                    SetupTheme()
//                    HomeScreen(composeNavigationFactories)
                }
            }

            ConnectivityStatus(get())
        }
    }

    @Composable
    private fun SetupTheme() {
        val systemUiController = rememberSystemUiController()
        val isLightTheme = !isSystemInDarkTheme()

        val systemBarColor = MaterialTheme.colors.surface.copy(alpha = 0.0f)
        val transparentColor: (Color) -> Color = { original ->
            systemBarColor.compositeOver(original)
        }
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = isLightTheme
            )

            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = isLightTheme,
                transformColorForLightContent = transparentColor
            )

            systemUiController.setNavigationBarColor(
                color = if (isLightTheme) LightColors.surface else DarkColors.primary,
                darkIcons = isLightTheme,
                navigationBarContrastEnforced = false,
                transformColorForLightContent = transparentColor
            )
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalCoroutinesApi
    @Composable
    fun ConnectivityStatus(observeNetwork: ObserveConnectionState) {
        val connection by connectivityState(observeNetwork)
        val isConnected = connection === ConnectionState.ConnectionAvailable

        var visibility by remember { mutableStateOf(false) }

        LaunchedEffect(isConnected) {
            visibility = if (!isConnected) {
                true
            } else {
                delay(2000)
                false
            }
        }

        AnimatedVisibility(
            visible = visibility,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            ConnectionStatus(isConnected = isConnected)
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(observeNetwork: ObserveConnectionState): State<ConnectionState> {
    return produceState(initialValue = observeNetwork.currentConnectivityState) {
        observeNetwork.observeConnectivityAsFlow()
            .distinctUntilChanged()
            .collect { value = it }
    }
}
