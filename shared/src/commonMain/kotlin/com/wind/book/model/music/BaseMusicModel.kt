package com.wind.book.model.music

import com.wind.book.Parcelable
import com.wind.book.Parcelize

@Parcelize
data class BaseMusicModel(
    val name: String,
    val picture: String,
    val pictureBig: String,
    val pictureMedium: String,
    val pictureSmall: String,
    val pictureXl: String
) : Parcelable {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        name = "",
        picture = "",
        pictureBig = "",
        pictureMedium = "",
        pictureSmall = "",
        pictureXl = "",
    )
}
