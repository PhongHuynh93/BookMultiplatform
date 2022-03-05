package com.wind.book.sharedUI

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp

val largeSpace = 24.dp
val aboveNormalSpace = 20.dp
val normalSpace = 16.dp
val smallSpace = 8.dp
val tinySpace = 8.dp

val Overlay = Color(0x66000000)

private val Slate200 = Color(0xFF81A9B3)
private val Slate600 = Color(0xFF4A6572)
private val Slate800 = Color(0xFF232F34)

private val Orange500 = Color(0xFFF9AA33)
private val Orange700 = Color(0xFFC78522)

private val LightColors = lightColors(
    primary = Slate800,
    onPrimary = Color.White,
    primaryVariant = Slate600,
    secondary = Orange700,
    secondaryVariant = Orange500,
    onSecondary = Color.Black,
)
private val DarkColors = darkColors(
    primary = Slate200,
    onPrimary = Color.Black,
    secondary = Orange500,
    onSecondary = Color.Black,
).withBrandedSurface()

fun Colors.withBrandedSurface() = copy(
    surface = primary.copy(alpha = 0.08f).compositeOver(this.surface),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        shapes = Shapes,
        typography = CocaTypography,
        content = content
    )
}

// preview not use typography, because it can not render
@Composable
fun PreviewAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        shapes = Shapes,
        content = content
    )
}
