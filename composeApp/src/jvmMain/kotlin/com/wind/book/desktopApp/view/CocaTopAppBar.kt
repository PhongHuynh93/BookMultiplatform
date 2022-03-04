package com.wind.book.desktopApp.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.wind.book.sharedUI.PreviewAppTheme
import com.wind.book.sharedUI.view.AppBarAlphas

@Composable
fun CocaTopAppBar(title: String, upAvailable: Boolean = false) {
    val navigator = LocalNavigator.currentOrThrow

    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.surface.copy(
            alpha = AppBarAlphas.translucentBarAlpha()
        ),
        contentColor = MaterialTheme.colors.onSurface,
        navigationIcon = if (upAvailable) {
            {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
        } else {
            null
        }
    )
}

@Preview
@Composable
fun CocaTopAppBarPreview() {
    PreviewAppTheme {
        CocaTopAppBar(title = "Title")
    }
}
