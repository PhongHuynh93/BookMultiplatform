package com.wind.touchdown.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.wind.book.model.touchdown.Product

@Composable
fun HomeScreen(
    onClickProductItem: (Product) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Surface(
            color = Color.White,
            elevation = 4.dp
        ) {
            HomeNavigationBarView(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(vertical = 8.dp)
            )
        }
        LazyColumn {
            item(key = "tab") {
                FeaturedTabView(modifier = Modifier.padding(vertical = 20.dp))
            }
            item(key = "category") {
                CategoryGridView(modifier = Modifier.padding(vertical = 12.dp))
            }
            item(key = "helmets") {
                TitleView(
                    title = "Helmets",
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                )
            }
            productGridView {
                onClickProductItem(it)
            }
            item(key = "brands") {
                TitleView(
                    title = "Brands",
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                )
            }
            item(key = "brandGrid") {
                BrandGridView()
            }
            item(key = "homeFooterView") {
                HomeFooterView(
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .padding(top = 16.dp)
                )
            }
            item("bottomNavSpace") {
                Spacer(
                    modifier = Modifier
                        .navigationBarsHeight()
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}
