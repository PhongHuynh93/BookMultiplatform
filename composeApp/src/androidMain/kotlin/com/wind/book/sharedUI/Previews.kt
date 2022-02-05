package com.wind.book.sharedUI

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wind.book.data.util.FakeData
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.sharedUI.view.music.GenreItem
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.LoadingState

@Preview
@Composable
fun GenreFeedPreview() {
    AppTheme {
        GenreFeed(
            state = LoadingState(
                LoadingScreen.Data(
                    data = (1..100).map {
                        FakeData.genre
                    }
                )
            )
        ) {}
    }
}

@Preview
@Composable
fun GenreItemPreview() {
    AppTheme {
        GenreItem(
            item = FakeData.genre
        )
    }
}
