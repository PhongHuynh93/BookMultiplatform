package com.wind.book.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.book.android.view.music.GenreScreen
import com.wind.book.sharedUI.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Navigator(GenreScreen())
                }
            }
        }
    }
}
