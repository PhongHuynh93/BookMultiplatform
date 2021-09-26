package com.wind.book.viewmodel.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
class PodcastNav(
    val id: String,
    val title: String,
    val publisher: String,
    val image: String,
    val thumbnail: String,
    val description: String,
    val totalEpisodes: Int
) :
    Parcelable
