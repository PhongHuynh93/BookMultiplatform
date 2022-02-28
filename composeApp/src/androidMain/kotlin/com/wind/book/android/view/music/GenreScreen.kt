package com.wind.book.android.view.music

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wind.book.android.R
import com.wind.book.android.view.CocaTopAppBar
import com.wind.book.sharedUI.normalSpace
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.genre.GenreViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.component.KoinComponent

class GenreScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val vm = getViewModel<GenreViewModel>()
        val state = vm.state.collectAsState()
//        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                CocaTopAppBar(title = stringResource(id = R.string.genre))
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
                Box {
                    GenreFeed(
                        state = state.value,
                        modifier = Modifier.fillMaxSize(),
                        contentPaddingValue = contentPaddingValue,
                        onClick = {
                            // TODO: go to album list
                        },
//                        onLoadMore = { index ->
//                            vm.loadMore(index)
//                        }
                    )
                }
            }
        }
    }
}
