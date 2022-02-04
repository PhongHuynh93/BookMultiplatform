package com.wind.book.sharedUI

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsHeight
import com.wind.book.android.R

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
    "Add" -> stringResource(R.string.app_name)
    "All" -> stringResource(R.string.app_name)
    "Remove" -> stringResource(R.string.app_name)
    "Rss feed url" -> stringResource(R.string.app_name)
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
