package org.shared.home

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import org.shared.navigation.ComposeNavigationFactory
import org.shared.navigation.NavigationScreen
import org.shared.navigation.addNavigation
import org.shared.tvmaniac.resources.R

@Composable
fun HomeScreen(
    composeNavigationFactories: Set<ComposeNavigationFactory>
) {

    val navController = rememberNavController()
    val route = currentRoute(navController)

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        bottomBar = {
            val currentSelectedItem by navController.currentScreenAsState()
            when {
                !route.contains(NavigationScreen.ShowDetailsNavScreen.route) &&
                    !route.contains(NavigationScreen.ShowGridNavScreen.route) &&
                    !route.contains(NavigationScreen.SeasonsNavScreen.route)
                -> {
                    TvManiacBottomNavigation(
                        onNavigationSelected = { selected ->
                            navController.navigate(selected.route) {
                                launchSingleTop = true
                                restoreState = true

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        },
                        currentSelectedItem = currentSelectedItem
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationScreen.DiscoverNavScreen.route
        ) {
            composeNavigationFactories.addNavigation(this, navController)
        }
    }
}

@Composable
private fun TvManiacBottomNavigation(
    onNavigationSelected: (NavigationScreen) -> Unit,
    currentSelectedItem: NavigationScreen
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {

        TvManiacBottomNavigationItem(
            screen = NavigationScreen.DiscoverNavScreen,
            imageVector = Icons.Outlined.Movie,
            title = stringResource(id = R.string.menu_item_discover),
            selected = currentSelectedItem == NavigationScreen.DiscoverNavScreen,
            onNavigationSelected = onNavigationSelected
        )

        TvManiacBottomNavigationItem(
            screen = NavigationScreen.SearchNavScreen,
            imageVector = Icons.Outlined.Search,
            title = stringResource(id = R.string.menu_item_search),
            selected = currentSelectedItem == NavigationScreen.SearchNavScreen,
            onNavigationSelected = onNavigationSelected
        )

        TvManiacBottomNavigationItem(
            screen = NavigationScreen.WatchlistNavScreen,
            imageVector = Icons.Outlined.Star,
            title = stringResource(id = R.string.menu_item_follow),
            selected = currentSelectedItem == NavigationScreen.WatchlistNavScreen,
            onNavigationSelected = onNavigationSelected
        )

        TvManiacBottomNavigationItem(
            screen = NavigationScreen.SettingsScreen,
            imageVector = Icons.Outlined.MoreVert,
            title = stringResource(id = R.string.menu_item_more),
            selected = currentSelectedItem == NavigationScreen.SettingsScreen,
            onNavigationSelected = onNavigationSelected
        )
    }
}

@Composable
fun RowScope.TvManiacBottomNavigationItem(
    screen: NavigationScreen,
    imageVector: ImageVector,
    title: String,
    selected: Boolean,
    onNavigationSelected: (NavigationScreen) -> Unit
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

@Composable
private fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: NavigationScreen.DiscoverNavScreen.route
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<NavigationScreen> {
    val selectedItem =
        remember { mutableStateOf<NavigationScreen>(NavigationScreen.DiscoverNavScreen) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == NavigationScreen.DiscoverNavScreen.route } -> {
                    selectedItem.value = NavigationScreen.DiscoverNavScreen
                }
                destination.hierarchy.any { it.route == NavigationScreen.SearchNavScreen.route } -> {
                    selectedItem.value = NavigationScreen.SearchNavScreen
                }
                destination.hierarchy.any { it.route == NavigationScreen.WatchlistNavScreen.route } -> {
                    selectedItem.value = NavigationScreen.WatchlistNavScreen
                }
                destination.hierarchy.any { it.route == NavigationScreen.SettingsScreen.route } -> {
                    selectedItem.value = NavigationScreen.SettingsScreen
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}
