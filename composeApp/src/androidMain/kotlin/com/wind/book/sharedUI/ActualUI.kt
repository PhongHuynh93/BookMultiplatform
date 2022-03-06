package com.wind.book.sharedUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wind.book.android.R
import com.wind.book.viewmodel.BaseViewModel

@Composable
internal actual fun AsyncImage(
    url: String,
    modifier: Modifier,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    colorFilter: ColorFilter?
) {
    Image(
        painter = rememberAsyncImagePainter(url),
        modifier = modifier,
        contentDescription = null,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

@Composable
internal actual fun LocalImage(
    resName: String,
    modifier: Modifier,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    colorFilter: ColorFilter?
) {
    Image(
        imageVector = ImageVector.vectorResource(resNameToId(resName)),
        modifier = modifier,
        contentDescription = null,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

// TODO:
private fun resNameToId(resName: String): Int = when (resName) {
    "ic_edit.xml" -> android.R.drawable.ic_delete
    "ic_add.xml" -> android.R.drawable.ic_delete
    else -> error("Unknown resName: $resName")
}

// TODO:
@Composable
internal actual fun _str(resName: String): String = when (resName) {
    "Genre" -> stringResource(R.string.genre)
    else -> error("Unknown resName: $resName")
}

internal actual fun Modifier.systemStatusBarsHeight(additional: Dp): Modifier =
    statusBarsHeight(additional)

internal actual fun Modifier.systemNavigationBarsHeight(additional: Dp): Modifier =
    navigationBarsHeight(additional)

internal actual fun Modifier.systemNavigationBarsWithImePadding(): Modifier =
    navigationBarsWithImePadding()

@Composable
internal actual fun Dialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) = androidx.compose.ui.window.Dialog(onDismissRequest = onDismissRequest, content = content)

internal actual fun _font(
    fontName: String,
    fontWeight: FontWeight
): Font {
    return Font(
        resId = when (fontName) {
            "inter_300.ttf" -> {
                R.font.inter_300
            }
            "inter_400.ttf" -> {
                R.font.inter_400
            }
            "inter_500.ttf" -> {
                R.font.inter_500
            }
            "inter_700.ttf" -> {
                R.font.inter_700
            }
            else -> {
                R.font.inter_400
            }
        },
        fontWeight
    )
}

@Composable
internal actual fun SwipeRefresh(
    isRefreshing: Boolean,
    indicatorPadding: PaddingValues,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        indicatorPadding = indicatorPadding,
        clipIndicatorToPadding = false,
        indicator = { indicatorState, refreshTriggerDistance ->
            SwipeRefreshIndicator(
                state = indicatorState,
                refreshTriggerDistance = refreshTriggerDistance,
                scale = true // https://github.com/google/accompanist/issues/572
            )
        },
        onRefresh = onRefresh,
        content = content
    )
}

actual typealias Screen = Screen

@Composable
actual inline fun <reified T : BaseViewModel> getViewModel(): T =
    org.koin.androidx.compose.getViewModel()

@Composable
actual fun insetTop(): Dp {
    return rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars
    ).calculateTopPadding()
}

@Composable
actual fun insetBottom(): Dp {
    return rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.navigationBars,
    ).calculateBottomPadding()
}
