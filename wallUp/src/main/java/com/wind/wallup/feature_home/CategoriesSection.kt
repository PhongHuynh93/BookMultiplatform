package com.wind.wallup.feature_home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wind.book.model.wallup.Category
import com.wind.wallup.extensions.gridItems
import com.wind.wallup.ui.SectionTitle

fun LazyListScope.categoriesSection(
    categories: List<Category>,
    onClickCategory: (Category) -> Unit
) {
    item(key = "categories_section") {
        SectionTitle(
            title = "Categories",
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
        )
    }
    gridItems(
        data = categories,
        columnCount = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        SingleCategoryItem(it, onClickCategory)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SingleCategoryItem(item: Category, onClickCategory: (Category) -> Unit) {
    Surface(
        onClick = { onClickCategory(item) },
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // TODO: handle image res
//            Image(
//                painter = painterResource(id = item.imageRes),
//                contentDescription = item.title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .scale(2F)
//            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White
                )
            )
        }
    }
}
