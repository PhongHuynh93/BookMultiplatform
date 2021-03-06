package com.wind.book.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.book.android.util.startActivity
import com.wind.book.android.view.music.ArtistActivity
import com.wind.book.sharedUI.AppTheme
import com.wind.book.sharedUI.view.music.GenreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    GenreScreen {
                        startActivity<ArtistActivity>(it)
                    }.Content()
                }
            }
        }
    }
}
