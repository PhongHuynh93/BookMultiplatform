package com.wind.book.sharedUI.view.music

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.wind.book.model.music.Genre
import com.wind.book.sharedUI.Screen
import com.wind.book.sharedUI.SwipeRefresh
import com.wind.book.sharedUI.getViewModel
import com.wind.book.sharedUI.insetBottom
import com.wind.book.sharedUI.normalSpace
import com.wind.book.sharedUI.view.CocaTopAppBar
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.artist.ArtistViewModel
import org.koin.core.component.KoinComponent

data class ArtistScreen(
    private val genre: Genre,
    private val onClickBack: (() -> Unit)? = null
) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val vm = getViewModel<ArtistViewModel>()
        val state = vm.state.collectAsState()
        vm.setGenreId(genre.id)
        Scaffold(
            topBar = {
                CocaTopAppBar(title = genre.model.name, onClickBack = onClickBack)
            }
        ) { paddingValues ->
            SwipeRefresh(
                isRefreshing = when (val screen = state.value.screen) {
                    is LoadingScreen.Data<*> -> screen.isRefresh
                    else -> false
                },
                indicatorPadding = paddingValues,
                onRefresh = { vm.refresh() }
            ) {
                val contentPaddingValue = PaddingValues(
                    start = normalSpace,
                    top = normalSpace + paddingValues.calculateTopPadding(),
                    end = normalSpace,
                    bottom = normalSpace + insetBottom(),
                )
                ArtistFeed(
                    state = state.value,
                    modifier = Modifier.fillMaxSize(),
                    contentPaddingValue = contentPaddingValue
                )
            }
        }
    }
}
