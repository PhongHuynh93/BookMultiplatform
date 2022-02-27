package com.wind.book.android.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.wind.book.sharedUI.PreviewAppTheme
import com.wind.book.sharedUI.view.AppBarAlphas

@Composable
fun CocaTopAppBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyBottom = false,
        ),
        backgroundColor = MaterialTheme.colors.surface.copy(
            alpha = AppBarAlphas.translucentBarAlpha()
        ),
        contentColor = MaterialTheme.colors.onSurface,
    )
}

@Preview
@Composable
fun CocaTopAppBarPreview() {
    PreviewAppTheme {
        CocaTopAppBar(title = "Title")
    }
}
