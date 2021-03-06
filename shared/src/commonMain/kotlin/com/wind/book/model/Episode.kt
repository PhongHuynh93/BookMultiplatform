package com.wind.book.model

import com.wind.book.Parcelable
import com.wind.book.Parcelize

@Parcelize
data class Episode(
    override val id: String,
    val title: String,
    val description: String,
    val pubDateMs: Long,
    val audio: String,
    val audioLengthSec: Int,
    val listennotesUrl: String,
    val image: String,
    val thumbnail: String,
    val maybeAudioInvalid: Boolean,
    val listennotesEditUrl: String,
    val explicitContent: Boolean,
    val link: String,
    val guidFromRss: String
) : Parcelable, Identifiable
