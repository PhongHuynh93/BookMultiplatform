package com.wind.book.android.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wind.book.data.util.FakeData
import com.wind.book.sharedUI.PreviewAppTheme
import com.wind.book.sharedUI.view.LoadingItem
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.viewmodel.LoadingState
import org.test.common_model.LoadingScreen

@Preview
@Composable
fun LoadingItemPreview() {
    PreviewAppTheme {
        LoadingItem()
    }
}

@Preview
@Composable
fun NoDataPreview() {
    PreviewAppTheme {
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
    PreviewAppTheme {
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
    PreviewAppTheme {
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
    PreviewAppTheme {
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
