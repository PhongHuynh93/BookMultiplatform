package com.wind.touchdown.ui.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wind.touchdown.ui.theme.PreviewAppTheme

@Composable
fun HomeFooterView(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        Text(
            text = "We offer the most cutting edge, comfortable, lightweight and durable football helmets in the market at affordable prices.",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
        AsyncImage(
            model = Uri.parse("file:///android_asset/logo/logo-lineal@3x.png"),
            contentDescription = null,
        )
        Text(
            text = "Copyright © Knot / knottx",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Composable
fun HomeFooterViewPreview() {
    PreviewAppTheme {
        HomeFooterView()
    }
}
