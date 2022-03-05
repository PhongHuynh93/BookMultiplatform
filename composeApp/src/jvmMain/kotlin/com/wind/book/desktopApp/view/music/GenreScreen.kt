package com.wind.book.desktopApp.view.music

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.wind.book.desktopApp.view.CocaTopAppBar
import com.wind.book.sharedUI._str
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.viewmodel.music.genre.GenreViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenreScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val vm: GenreViewModel by inject()
        val state = vm.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CocaTopAppBar(
                    title = _str("Genre")
                )
            }
        ) {
            GenreFeed(
                state = state.value,
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    navigator.push(ArtistScreen(it))
                }
            )
        }
    }
}
