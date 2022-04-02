package com.wind.wallup.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wind.book.model.wallup.WallupCategory
import com.wind.wallup.R
import com.wind.wallup.extensions.gridItems
import com.wind.wallup.ui.SectionTitle

fun LazyListScope.categoriesSection(
    wallupCategories: List<WallupCategory>,
    onClickCategory: (WallupCategory) -> Unit
) {
    item(key = "categories_section") {
        SectionTitle(
            title = "Categories",
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
        )
    }
    gridItems(
        data = wallupCategories,
        columnCount = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        SingleCategoryItem(it, onClickCategory)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SingleCategoryItem(item: WallupCategory, onClickCategory: (WallupCategory) -> Unit) {
    Surface(
        onClick = { onClickCategory(item) },
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val imageRes = when (item) {
                WallupCategory.Abstract -> R.drawable.ic_abstract
                WallupCategory.Animals -> R.drawable.ic_animals
                WallupCategory.Anime -> R.drawable.ic_anime
                WallupCategory.Art -> R.drawable.ic_arts
                WallupCategory.Cars -> R.drawable.ic_cars
                WallupCategory.City -> R.drawable.ic_city
                WallupCategory.Dark -> R.drawable.ic_dark
                WallupCategory.Flowers -> R.drawable.ic_flowers
                WallupCategory.Food -> R.drawable.ic_food
                WallupCategory.Holidays -> R.drawable.ic_holidays
                WallupCategory.Love -> R.drawable.ic_love
                WallupCategory.Macro -> R.drawable.ic_macro
                WallupCategory.Motorcycles -> R.drawable.ic_motorcycles
                WallupCategory.Music -> R.drawable.ic_music
                WallupCategory.Nature -> R.drawable.ic_nature
                WallupCategory.Space -> R.drawable.ic_space
                WallupCategory.Sport -> R.drawable.ic_sports
                WallupCategory.Technologies -> R.drawable.ic_tech
                WallupCategory.Vector -> R.drawable.ic_vector
                WallupCategory.Words -> R.drawable.ic_words
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(2F)
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White
                )
            )
        }
    }
}
