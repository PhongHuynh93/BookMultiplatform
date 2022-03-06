package com.wind.book.sharedUI

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wind.book.viewmodel.BaseViewModel

@Composable
internal expect fun AsyncImage(
    url: String,
    modifier: Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
)

@Composable
internal expect fun LocalImage(
    resName: String,
    modifier: Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
)

@Composable
internal expect fun Dialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
)

@Composable
internal expect fun _str(resName: String): String

internal expect fun Modifier.systemStatusBarsHeight(additional: Dp = 0.dp): Modifier
internal expect fun Modifier.systemNavigationBarsHeight(additional: Dp = 0.dp): Modifier
internal expect fun Modifier.systemNavigationBarsWithImePadding(): Modifier

internal expect fun _font(fontName: String, fontWeight: FontWeight): Font

@Composable
internal expect fun SwipeRefresh(
    isRefreshing: Boolean = false,
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit
)

expect interface Screen {
    @Composable
    fun Content()
}

@Composable
expect inline fun <reified T : BaseViewModel> getViewModel(): T

@Composable
expect fun insetTop(): Dp
expect fun insetBottom(): Dp
