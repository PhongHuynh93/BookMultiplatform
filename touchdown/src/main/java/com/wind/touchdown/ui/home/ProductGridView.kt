package com.wind.touchdown.ui.home

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wind.book.model.touchdown.Product
import com.wind.touchdown.extensions.verticalGridItems
import com.wind.touchdown.products
import com.wind.touchdown.ui.theme.PreviewAppTheme

fun LazyListScope.productGridView(
    onClickItem: (Product) -> Unit = {}
) {
    verticalGridItems(
        data = products,
        columnCount = 2,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        ProductItemView(
            product = it,
            modifier = Modifier.clickable {
                onClickItem(it)
            }
        )
    }
}

@Composable
fun ProductItemView(product: Product, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier) {
        Surface(
            color = Color(
                red = product.color[0].toFloat(),
                green = product.color[1].toFloat(),
                blue = product.color[2].toFloat(),
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            AsyncImage(
                model = Uri.parse("file:///android_asset/helmet/${product.image}.png"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.padding(all = 12.dp)
            )
        }
        Text(
            text = product.name,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = product.formattedPrice(),
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun ProductItemViewPreview() {
    PreviewAppTheme {
        ProductItemView(product = products.first())
    }
}
