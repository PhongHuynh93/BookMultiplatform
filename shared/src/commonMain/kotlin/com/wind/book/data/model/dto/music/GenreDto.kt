package com.wind.book.data.model.dto.music

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("picture")
    val picture: String? = null,
    @SerialName("picture_big")
    val pictureBig: String? = null,
    @SerialName("picture_medium")
    val pictureMedium: String? = null,
    @SerialName("picture_small")
    val pictureSmall: String? = null,
    @SerialName("picture_xl")
    val pictureXl: String? = null,
    @SerialName("type")
    val type: String? = null
)
