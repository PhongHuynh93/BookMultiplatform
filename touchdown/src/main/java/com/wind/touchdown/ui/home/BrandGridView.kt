package com.wind.touchdown.ui.home

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wind.book.model.touchdown.Brand
import com.wind.touchdown.brands
import com.wind.touchdown.extensions.horizontalGridItems

@Composable
fun BrandGridView(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        item(key = "brandStartSpace") {
            Spacer(modifier = Modifier.padding(end = 4.dp))
        }
        horizontalGridItems(
            data = brands,
            rowCount = 2,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(start = 12.dp)
        ) {
            BrandItemView(brand = it)
        }
        item(key = "brandEndSpace") {
            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
    }
}

@Composable
fun BrandItemView(brand: Brand) {
    OutlinedButton(
        onClick = { /*TODO*/ },
        border = BorderStroke(1.dp, Color.Gray),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
        contentPadding = PaddingValues(all = 12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        AsyncImage(
            model = Uri.parse("file:///android_asset/brand/${brand.image}@3x.png"),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
