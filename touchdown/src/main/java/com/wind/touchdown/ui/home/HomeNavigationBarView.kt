package com.wind.touchdown.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wind.touchdown.R
import com.wind.touchdown.ui.theme.AppTheme
import com.wind.touchdown.ui.theme.PreviewAppTheme

@Composable
fun HomeNavigationBarView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.Search, contentDescription = null)
        }
        Spacer(modifier = Modifier.weight(1f))
        LogoView()
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Box {
                Icon(Icons.Outlined.ShoppingCart, contentDescription = null)
                Surface(
                    color = Color.Red,
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                }
            }
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun HomeNavigationBarViewPreview() {
    PreviewAppTheme {
        HomeNavigationBarView(
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}

@Composable
fun LogoView(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "Touch".uppercase(),
            fontWeight = FontWeight.Black,
            style = MaterialTheme.typography.subtitle1
        )
        Image(painter = painterResource(R.drawable.logo), contentDescription = "")
        Text(
            text = "Down".uppercase(),
            fontWeight = FontWeight.Black,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
fun LogoViewPreview() {
    AppTheme {
        Surface(
            color = Color.White
        ) {
            LogoView(
                modifier = Modifier.padding(all = 8.dp)
            )
        }
    }
}
