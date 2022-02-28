package com.wind.book.data.mapping.music

import com.wind.book.data.mapping.Mapper
import com.wind.book.data.model.dto.music.ArtistDto
import com.wind.book.model.music.Artist
import com.wind.book.model.music.BaseMusicModel

class ArtistMapper : Mapper<ArtistDto, Artist> {
    override fun apply(input: ArtistDto): Artist {
        return Artist(
            id = input.id.orEmpty(),
            model =
            BaseMusicModel(
                name = input.name.orEmpty(),
                picture = input.picture.orEmpty(),
                pictureBig = input.pictureBig.orEmpty(),
                pictureMedium = input.pictureMedium.orEmpty(),
                pictureSmall = input.pictureSmall.orEmpty(),
                pictureXl = input.pictureXl.orEmpty(),
            ),
            radio = input.radio ?: false,
            trackList = input.tracklist.orEmpty()
        )
    }
}
