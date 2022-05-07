package org.navigation.navigation

sealed class NavigationScreen {
    object DiscoverNavScreen : NavigationScreen()
    object SearchNavScreen : NavigationScreen()
    object WatchlistNavScreen : NavigationScreen()
    object ShowDetailsNavScreen : NavigationScreen()
    object SettingsScreen : NavigationScreen()
    object ShowGridNavScreen : NavigationScreen()
    object SeasonsNavScreen : NavigationScreen()
}
