package com.wind.book.sharedUI.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.wind.book.sharedUI.insetTop

@Composable
fun CocaTopAppBar(
    title: String,
    onClickBack: (() -> Unit)? = null
) {
    Surface(
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.surface.copy(
            alpha = AppBarAlphas.translucentBarAlpha()
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(insetTop()))
            TopAppBar(
                title = {
                    Text(text = title)
                },
                backgroundColor = Transparent,
                contentColor = MaterialTheme.colors.onSurface,
                navigationIcon = if (onClickBack != null) {
                    {
                        IconButton(onClick = onClickBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                } else {
                    null
                },
                elevation = 0.dp
            )
        }
    }
}
