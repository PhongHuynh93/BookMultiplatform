package com.wind.book.android.view.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.book.android.util.SavedStateHandleHelper
import com.wind.book.model.music.Genre
import com.wind.book.sharedUI.AppTheme
import com.wind.book.sharedUI.view.music.ArtistScreen

class ArtistActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    ArtistScreen(
                        genre = SavedStateHandleHelper<Genre>().getData(intent = intent),
                        onClickBack = {
                            onBackPressed()
                        }
                    ).Content()
                }
            }
        }
    }
}
