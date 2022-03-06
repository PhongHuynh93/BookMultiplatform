package com.wind.book.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.book.sharedUI.AppTheme
import com.wind.book.sharedUI.view.music.GenreScreen

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Navigator(
                        GenreScreen(
                            onClickGenre = {}
                        )
                    ) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}
