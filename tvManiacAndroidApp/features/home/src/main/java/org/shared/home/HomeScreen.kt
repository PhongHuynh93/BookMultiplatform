package org.shared.home

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.shared.tvmaniac.resources.R

private const val TAB_DISCOVER = 0
private const val TAB_SEARCH = 1
private const val TAB_WATCH_LIST = 2
private const val TAB_SETTING = 3

@Composable
fun HomeScreen() {

    val mapTab = mapOf<Int, @Composable () -> Unit>(
        TAB_DISCOVER to { DiscoverScreen() },
        TAB_SEARCH to { SearchScreen() },
        TAB_WATCH_LIST to { FollowingContent() },
        TAB_SETTING to { SettingsScreen() },
    )

    val pagerState = rememberPagerState()

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        bottomBar = {
            TvManiacBottomNavigation(
                onNavigationSelected = {
                    scope.launch {
                        pagerState.scrollToPage(page = it)
                    }
                },
                currentSelectedItem = pagerState.currentPage
            )
        }
    ) {
        HorizontalPager(count = mapTab.size, state = pagerState) { page ->
            // Our page content
            mapTab[page]!!.invoke()
        }
    }
}

@Composable
fun DiscoverScreen() {
    Text(text = "Discover")
}

@Composable
fun SearchScreen() {
    Text(text = "Search")
}

@Composable
fun FollowingContent() {
    Text(text = "Following")
}

@Composable
fun SettingsScreen() {
    Text(text = "Setting")
}

@Composable
private fun TvManiacBottomNavigation(
    onNavigationSelected: (Int) -> Unit,
    currentSelectedItem: Int
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {

        TvManiacBottomNavigationItem(
            screen = TAB_DISCOVER,
            imageVector = Icons.Outlined.Movie,
            title = stringResource(id = R.string.menu_item_discover),
            selected = currentSelectedItem == TAB_DISCOVER,
            onNavigationSelected = onNavigationSelected
        )

        TvManiacBottomNavigationItem(
            screen = TAB_SEARCH,
            imageVector = Icons.Outlined.Search,
            title = stringResource(id = R.string.menu_item_search),
            selected = currentSelectedItem == TAB_SEARCH,
            onNavigationSelected = onNavigationSelected
        )

        TvManiacBottomNavigationItem(
            screen = TAB_WATCH_LIST,
            imageVector = Icons.Outlined.Star,
            title = stringResource(id = R.string.menu_item_follow),
            selected = currentSelectedItem == TAB_WATCH_LIST,
            onNavigationSelected = onNavigationSelected
        )

        TvManiacBottomNavigationItem(
            screen = TAB_SETTING,
            imageVector = Icons.Outlined.MoreVert,
            title = stringResource(id = R.string.menu_item_more),
            selected = currentSelectedItem == TAB_SETTING,
            onNavigationSelected = onNavigationSelected
        )
    }
}

@Composable
fun RowScope.TvManiacBottomNavigationItem(
    screen: Int,
    imageVector: ImageVector,
    title: String,
    selected: Boolean,
    onNavigationSelected: (Int) -> Unit
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = title
            )
        },
        label = { Text(title) },
        selected = selected,
        alwaysShowLabel = true,
        selectedContentColor = MaterialTheme.colors.secondary,
        unselectedContentColor = MaterialTheme.colors.onSurface,
        onClick = { onNavigationSelected(screen) }
    )
}
