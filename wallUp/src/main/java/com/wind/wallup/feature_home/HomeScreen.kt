package com.wind.wallup.feature_home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wind.book.log
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.model.wallup.Home
import com.wind.book.viewmodel.wallup.home.HomeEffect
import com.wind.book.viewmodel.wallup.home.HomeViewModel
import com.wind.wallup.ui.LoadingItem
import com.wind.wallup.ui.animation.ScreensAnim
import com.wind.wallup.utils.ShakeManager
import com.wind.wallup.utils.tryCast
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Destination(
    style = ScreensAnim::class
)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    // not used
    log.d { "$navigator" }
    val vm = getViewModel<HomeViewModel>()
    val state = vm.state.collectAsState()
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = true) {
        vm.effect.collectLatest {
            when (it) {
//                is UiEvent.OnNavigate -> navigator.navigate(it.route)
                HomeEffect.ScrollToTop -> scrollState.animateScrollToItem(0)
            }
        }
    }
    ShakeManager(systemAction = Context.SENSOR_SERVICE) {
        vm.onShake()
    }
    when (val screen = state.value.screen) {
        is LoadingScreen.Data<*> -> {
            screen.tryCast<LoadingScreen.Data<Home>> {
                val data = data
                LazyColumn(
                    modifier = Modifier.background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(top = 50.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = scrollState
                ) {
                    welcomeSection()
                    suggestedSection((data[2] as Home.RandomPhotoList).randomPhotoList) {
                        // TODO: on click photo
                    }
                    colorSection((data[0] as Home.ColorList).colorList) {
                        // TODO: on click color
                    }
                    categoriesSection((data[1] as Home.CategoryList).categoryList) {
                        // TODO: on click category
                    }
                }
            }
        }
        is LoadingScreen.Error -> Text(text = screen.errorMessage)
        LoadingScreen.Loading -> LoadingItem()
        is LoadingScreen.NoData -> Text(text = screen.message)
    }
}
