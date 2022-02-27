package com.wind.book.desktopApp.view.music

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.wind.book.sharedUI._str
import com.wind.book.sharedUI.view.AppBarAlphas
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.viewmodel.music.genre.GenreViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenreScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val vm: GenreViewModel by inject()
        val state = vm.state.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = _str("Genre"))
                    },
                    backgroundColor = MaterialTheme.colors.surface.copy(
                        alpha = AppBarAlphas.translucentBarAlpha()
                    ),
                    contentColor = MaterialTheme.colors.onSurface,
                )
            }
        ) {
            GenreFeed(
                state = state.value,
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    // TODO: go to album list
                }
            )
        }
    }
}
