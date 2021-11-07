package com.wind.book.model.music

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.wind.book.model.Identifiable

@Parcelize
data class Genre(
    override val id: String,
    val model: BaseMusicModel,
) : Parcelable, Identifiable {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        id = "",
        model = BaseMusicModel()
    )
}
