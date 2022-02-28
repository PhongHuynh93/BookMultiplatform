package com.wind.book.data.mapping.music

import com.wind.book.data.mapping.Mapper
import com.wind.book.data.model.dto.music.GenreDto
import com.wind.book.model.music.BaseMusicModel
import com.wind.book.model.music.Genre

class GenreMapper : Mapper<GenreDto, Genre> {
    override fun apply(input: GenreDto): Genre {
        return Genre(
            id = input.id.orEmpty(),
            model =
            BaseMusicModel(
                name = input.name.orEmpty(),
                picture = input.picture.orEmpty(),
                pictureBig = input.pictureBig.orEmpty(),
                pictureMedium = input.pictureMedium.orEmpty(),
                pictureSmall = input.pictureSmall.orEmpty(),
                pictureXl = input.pictureXl.orEmpty(),
            )
        )
    }
}
