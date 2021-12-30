package com.wind.book.data.util

import com.benasher44.uuid.uuid4
import com.wind.book.model.music.Artist
import com.wind.book.model.music.BaseMusicModel
import com.wind.book.model.music.Genre

/**
 * Fake data for compose and swiftUI preview
 */
object FakeData {
    private val mockPopPicture = "https://i.ytimg.com/vi/0CWUFzbXVJg/maxresdefault.jpg"
    private val mockArtistPicture = "https://lh3.googleusercontent.com/_fnSo5pFwGb7QJZL6iOTYkHwSJ9yvA16yKZRHUTDodzKTu3kUFu9apc69J8SlP-Q2HUymWy4TNxK4B9mUhubl01d"

    val genre = Genre(
        id = uuid4().toString(),
        model = BaseMusicModel(
            name = "Pop",
            picture = mockPopPicture,
            pictureBig = mockPopPicture,
            pictureMedium = mockPopPicture,
            pictureSmall = mockPopPicture,
            pictureXl = mockPopPicture,
        )
    )

    val genreList = generateGenre(50)

    private fun generateGenre(size: Int): List<Genre> {
        val result = mutableListOf<Genre>()
        repeat(size) {
            result.add(genre)
        }
        return result
    }

    val artist = Artist(
        id = uuid4().toString(),
        model = BaseMusicModel(
            name = "Taylor Swift",
            picture = mockArtistPicture,
            pictureBig = mockArtistPicture,
            pictureMedium = mockArtistPicture,
            pictureSmall = mockArtistPicture,
            pictureXl = mockArtistPicture,
        ),
        radio = false,
        trackList = ""
    )
}
