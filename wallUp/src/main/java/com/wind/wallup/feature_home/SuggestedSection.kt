package com.wind.wallup.feature_home

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.wind.book.data.util.FakeWallupData
import com.wind.book.model.wallup.UnsplashPhoto
import com.wind.wallup.ui.SectionTitle
import com.wind.wallup.ui.theme.PreviewAppTheme

fun LazyListScope.suggestedSection(
    data: List<UnsplashPhoto>?,
    onClickPhoto: (UnsplashPhoto) -> Unit
) {
    if (data != null) item(key = "suggested_section") {
        SuggestedSection(data, onClickPhoto)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.SuggestedSection(
    photos: List<UnsplashPhoto>,
    onClickPhoto: (UnsplashPhoto) -> Unit
) {
    Column(
        modifier = Modifier.animateItemPlacement(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionTitle(
            title = "Suggested for you",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(count = photos.size, key = { index -> index }) { index ->
                SingleSuggestedItem(photos[index], onClickPhoto)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SingleSuggestedItem(data: UnsplashPhoto, onClickPhoto: (UnsplashPhoto) -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val scaleAnim by animateFloatAsState(
        targetValue = if (isClicked) 1.05F else 1F,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy
        ),
    )
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 250.dp)
            .scale(scaleAnim),
        onClick = {
            isClicked = isClicked.not()
            onClickPhoto(data)
        },
        elevation = 2.dp,
        color = Color(android.graphics.Color.parseColor(data.color)),
        shape = RoundedCornerShape(10.dp)
    ) {
        val painter = rememberAsyncImagePainter(data.smallImage)

        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = data.userDetail.name
        )
    }
}

@Preview
@Composable
fun SuggestedSectionPreview() {
    PreviewAppTheme {
        LazyColumn {
            suggestedSection(
                data = listOf(FakeWallupData.unsplashPhoto)
            ) {
            }
        }
    }
}
