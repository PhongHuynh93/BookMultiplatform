package com.wind.touchdown.ui.home

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.wind.touchdown.ui.theme.PreviewAppTheme

@Composable
fun TitleView(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Black,
        modifier = modifier
    )
}

@Preview
@Composable
fun TitleViewPreview() {
    PreviewAppTheme {
        TitleView("Helmet")
    }
}
