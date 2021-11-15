package com.wind.book.model.music

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

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
