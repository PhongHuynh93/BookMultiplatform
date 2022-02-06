package com.wind.book.sharedUI.view.music

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.wind.book.model.music.Genre
import com.wind.book.sharedUI.AsyncImage
import com.wind.book.sharedUI.Overlay
import com.wind.book.sharedUI.Shapes
import com.wind.book.sharedUI.normalSpace
import com.wind.book.sharedUI.util.tryCast
import com.wind.book.sharedUI.view.LoadingItem
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.LoadingState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreFeed(
    state: LoadingState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (val screen = state.screen) {
            is LoadingScreen.Data<*> -> {
                screen.tryCast<LoadingScreen.Data<Genre>> {
                    val data = data
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
                        contentPadding = PaddingValues(all = normalSpace),
                        horizontalArrangement = Arrangement.spacedBy(normalSpace),
                        verticalArrangement = Arrangement.spacedBy(normalSpace),
                    ) {
                        itemsIndexed(data) { _, item ->
                            GenreItem(
                                item = item,
                                onClick = onClick
                            )
                        }
                        // FIXME: wait for span in vertical
                        // https://stackoverflow.com/questions/65981114/does-jetpack-composes-lazyverticalgrid-have-span-strategy
                        item {
                            if (screen.errorMessage != null) {
                                Text(text = screen.errorMessage!!)
                            } else if (!screen.isEndPage) {
                                LoadingItem()
                            }
                        }
                    }
                }
            }
            is LoadingScreen.Error -> Text(text = screen.errorMessage)
            LoadingScreen.Loading -> LoadingItem()
            is LoadingScreen.NoData -> Text(text = screen.message)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenreItem(
    item: Genre,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(Shapes.medium)
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            url = item.model.pictureMedium,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(2f)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Overlay)
        )
        Text(
            text = item.model.name,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
