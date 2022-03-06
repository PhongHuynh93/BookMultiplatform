package com.wind.book.desktopApp.view.music

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.wind.book.model.music.Genre
import com.wind.book.sharedUI.view.CocaTopAppBar
import com.wind.book.sharedUI.view.music.ArtistFeed
import com.wind.book.viewmodel.music.artist.ArtistViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class ArtistScreen(private val genre: Genre) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val vm: ArtistViewModel by inject()
        val state = vm.state.collectAsState()
        vm.setGenreId(genre.id)
        // TODO: handle back button
        Scaffold(
            topBar = {
                CocaTopAppBar(
                    title = genre.model.name,
                )
            }
        ) {
            ArtistFeed(
                state = state.value,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
