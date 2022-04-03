package com.wind.wallup.ui.animation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.wind.wallup.destinations.SplashScreenDestination
import com.wind.wallup.navDestination

@OptIn(ExperimentalAnimationApi::class)
object ScreensAnim : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.navDestination) {
            SplashScreenDestination -> null
            else -> slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it })
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return slideOutHorizontally(
            animationSpec = tween(300),
            targetOffsetX = { (-it / 3) * 2 }
        ) + fadeOut(
            animationSpec = tween(durationMillis = 300), targetAlpha = 0.3F
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
        return slideInHorizontally(animationSpec = tween(300), initialOffsetX = { -it }) + fadeIn(
            animationSpec = tween(durationMillis = 300), initialAlpha = 0.3F
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it })
    }
}
