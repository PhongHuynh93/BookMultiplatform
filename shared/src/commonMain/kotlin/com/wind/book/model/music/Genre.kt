package com.wind.book.model.music

import com.wind.book.Parcelable
import com.wind.book.Parcelize
import com.wind.book.model.Identifiable

@Parcelize
data class Genre(
    override val id: String,
    val model: BaseMusicModel,
) : Parcelable, Identifiable
