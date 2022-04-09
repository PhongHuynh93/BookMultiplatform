package com.wind.touchdown.ui.home

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wind.book.model.touchdown.TouchDownCategory
import com.wind.touchdown.extensions.horizontalGridItems
import com.wind.touchdown.touchDownCategories
import com.wind.touchdown.ui.theme.PreviewAppTheme

@Composable
fun CategoryGridView(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        item(key = "categoryStartSpace") {
            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
        item(key = "categoryStartHeader") {
            CategorySectionView(rotateClockwise = false, modifier = Modifier.height(120.dp))
        }
        item(key = "categoryStartGridSpace") {
            Spacer(modifier = Modifier.padding(end = 4.dp))
        }
        horizontalGridItems(
            data = touchDownCategories,
            rowCount = 2,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(start = 12.dp)
        ) {
            CategoryItemView(category = it)
        }
        item(key = "categoryEndSpace") {
            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
        item(key = "categoryEndHeader") {
            CategorySectionView(rotateClockwise = true, modifier = Modifier.height(120.dp))
        }
        item(key = "categoryEndHeaderSpace") {
            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
    }
}

@Composable
fun CategorySectionView(rotateClockwise: Boolean, modifier: Modifier = Modifier) {
    Surface(
        color = Color.DarkGray,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.width(85.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Categories".uppercase(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .rotate(
                        if (rotateClockwise) {
                            90f
                        } else {
                            -90f
                        }
                    ),
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun CategorySectionRotateClockwiseViewPreview() {
    PreviewAppTheme {
        CategorySectionView(rotateClockwise = true, modifier = Modifier.height(120.dp))
    }
}

@Preview
@Composable
fun CategorySectionRotateCounterclockwiseViewPreview() {
    PreviewAppTheme {
        CategorySectionView(rotateClockwise = false, modifier = Modifier.height(120.dp))
    }
}

@Composable
fun CategoryItemView(category: TouchDownCategory) {
    OutlinedButton(
        onClick = { /*TODO*/ },
        border = BorderStroke(1.dp, Color.Gray),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
        contentPadding = PaddingValues(all = 12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = Uri.parse("file:///android_asset/category/${category.image}@3x.png"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = category.name.uppercase(),
                fontWeight = FontWeight.Light
            )
        }
    }
}

// Note - currently can not preview bitmap
// @Preview
// @Composable
// fun CategoryItemViewPreview() {
//    PreviewAppTheme {
//        CategoryItemView(touchDownCategories[0])
//    }
// }
//
// @Preview
// @Composable
// fun CategoryGridViewPreview() {
//    PreviewAppTheme {
//        CategoryGridView()
//    }
// }
