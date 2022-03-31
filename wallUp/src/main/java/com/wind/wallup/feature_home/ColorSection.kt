package com.wind.wallup.feature_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wind.book.model.wallup.ColorItem
import com.wind.wallup.ui.SectionTitle

fun LazyListScope.colorSection(
    colors: List<ColorItem>,
    onClickColor: (ColorItem) -> Unit
) {
    item(key = "color_section") {
        ColorSection(colors = colors, onClickColor)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.ColorSection(
    colors: List<ColorItem>,
    onClickColor: (ColorItem) -> Unit
) {
    Column(
        modifier = Modifier.animateItemPlacement(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionTitle(
            title = "The color tone",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(count = colors.size, key = { it }) {
                SingleColorItem(colors[it], onClickColor)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SingleColorItem(item: ColorItem, onClickColor: (ColorItem) -> Unit) {
    Surface(
        onClick = {
            onClickColor(item)
        },
        modifier = Modifier.size(50.dp),
        color = Color(android.graphics.Color.parseColor(item.hexCode)),
        shape = RoundedCornerShape(10.dp)
    ) {
    }
}
