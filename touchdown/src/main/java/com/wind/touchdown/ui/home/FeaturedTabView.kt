package com.wind.touchdown.ui.home

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.wind.touchdown.players

@Composable
fun FeaturedTabView(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = players.size,
            state = pagerState,
        ) { page ->
            val player = players[page]
            // Our page content
            AsyncImage(
                model = Uri.parse("file:///android_asset/player/${player.image}.jpg"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 10f)
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.White,
            inactiveColor = Color.White.copy(alpha = 0.4f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp),
        )
    }
}

// Note - currently can not preview bitmap
// @Preview
// @Composable
// fun FeaturedTabViewPreview() {
//    PreviewAppTheme {
//        FeaturedTabView()
//    }
// }
