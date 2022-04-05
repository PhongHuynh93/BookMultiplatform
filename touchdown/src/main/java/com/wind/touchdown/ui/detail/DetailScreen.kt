package com.wind.touchdown.ui.detail

import androidx.compose.runtime.Composable
import com.wind.book.log
import com.wind.book.model.touchdown.Product

@Composable
fun DetailScreen(product: Product) {
    log.e { "product $product" }
}
