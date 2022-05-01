package com.wind.book.android.view.music

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wind.book.data.util.FakeData
import com.wind.book.sharedUI.PreviewAppTheme
import com.wind.book.sharedUI.view.CocaTopAppBar
import com.wind.book.sharedUI.view.music.ArtistFeed
import com.wind.book.sharedUI.view.music.ArtistItem
import com.wind.book.sharedUI.view.music.GenreFeed
import com.wind.book.sharedUI.view.music.GenreItem
import com.wind.book.viewmodel.LoadingState
import org.test.common_model.LoadingScreen

@Preview
@Composable
fun GenreFeedPreview() {
    PreviewAppTheme {
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
    PreviewAppTheme {
        GenreItem(
            item = FakeData.genre
        )
    }
}

@Preview
@Composable
fun ArtistFeedPreview() {
    PreviewAppTheme {
        ArtistFeed(
            state = LoadingState(
                LoadingScreen.Data(
                    data = (1..100).map {
                        FakeData.artist
                    }
                )
            )
        ) {}
    }
}

@Preview
@Composable
fun ArtistItemPreview() {
    PreviewAppTheme {
        ArtistItem(
            item = FakeData.artist
        )
    }
}

@Preview
@Composable
fun CocaTopAppBarPreview() {
    PreviewAppTheme {
        CocaTopAppBar(title = "Title")
    }
}
