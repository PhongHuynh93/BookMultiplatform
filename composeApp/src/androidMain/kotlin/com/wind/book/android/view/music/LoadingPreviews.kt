package com.wind.book.android.view.music

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wind.book.data.util.FakeData
import com.wind.book.sharedUI.AppTheme
import com.wind.book.sharedUI.view.LoadingItem
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.LoadingState

@Preview
@Composable
fun LoadingItemPreview() {
    AppTheme {
        LoadingItem()
    }
}

@Preview
@Composable
fun NoDataPreview() {
    AppTheme {
        GenreFeed(
            state = LoadingState(
                LoadingScreen.NoData("No data")
            )
        )
    }
}

@Preview
@Composable
fun LoadingErrorErrorPreview() {
    AppTheme {
        GenreFeed(
            state = LoadingState(
                LoadingScreen.Error("No data")
            )
        )
    }
}

@Preview
@Composable
fun LoadMorePreview() {
    AppTheme {
        GenreFeed(
            state = LoadingState(
                LoadingScreen.Data(
                    data = (1..4).map {
                        FakeData.genre
                    },
                    isRefresh = false,
                    isEndPage = false,
                    errorMessage = null
                )
            )
        )
    }
}

@Preview
@Composable
fun LoadMoreErrorPreview() {
    AppTheme {
        GenreFeed(
            state = LoadingState(
                LoadingScreen.Data(
                    data = (1..4).map {
                        FakeData.genre
                    },
                    isRefresh = false,
                    isEndPage = true,
                    errorMessage = "Something went wrong"
                )
            )
        )
    }
}
