package com.wind.book.data.mapping.music

import com.wind.book.data.mapping.Mapper
import com.wind.book.data.model.dto.music.AlbumDto
import com.wind.book.model.music.Album
import com.wind.book.model.music.BaseMusicModel

class AlbumMapper : Mapper<AlbumDto, Album> {

    override fun apply(input: AlbumDto): Album {
        return Album(
            id = input.id.orEmpty(),
            model =
            BaseMusicModel(
                name = input.title.orEmpty(),
                picture = input.cover.orEmpty(),
                pictureBig = input.coverBig.orEmpty(),
                pictureMedium = input.coverMedium.orEmpty(),
                pictureSmall = input.coverSmall.orEmpty(),
                pictureXl = input.coverXl.orEmpty(),
            ),
            releaseDate = input.releaseDate.orEmpty()
        )
    }
}
