package com.wind.book.android.view.music

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wind.book.model.music.Genre
import com.wind.book.sharedUI.normalSpace
import com.wind.book.sharedUI.view.CocaTopAppBar
import com.wind.book.sharedUI.view.music.ArtistFeed
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.artist.ArtistViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.component.KoinComponent

data class ArtistScreen(private val genre: Genre) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val vm = getViewModel<ArtistViewModel>()
        val state = vm.state.collectAsState()
        vm.setGenreId(genre.id)
        Scaffold(
            topBar = {
                CocaTopAppBar(title = genre.model.name) // TODO: handle back
            }
        ) { paddingValues ->
            SwipeRefresh(
                state = rememberSwipeRefreshState(
                    when (val screen = state.value.screen) {
                        is LoadingScreen.Data<*> -> screen.isRefresh
                        else -> false
                    }
                ),
                indicatorPadding = paddingValues,
                clipIndicatorToPadding = false,
                indicator = { indicatorState, refreshTriggerDistance ->
                    SwipeRefreshIndicator(
                        state = indicatorState,
                        refreshTriggerDistance = refreshTriggerDistance,
                        scale = true // https://github.com/google/accompanist/issues/572
                    )
                },
                onRefresh = { vm.refresh() }
            ) {
                val contentPaddingValue = PaddingValues(
                    start = normalSpace,
                    top = normalSpace + paddingValues.calculateTopPadding(),
                    end = normalSpace,
                    bottom = normalSpace + rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.navigationBars,
                        applyTop = false,
                        applyBottom = true,
                    ).calculateBottomPadding(),
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
