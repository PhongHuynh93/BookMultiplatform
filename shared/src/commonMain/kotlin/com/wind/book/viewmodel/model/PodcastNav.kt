package com.wind.book.viewmodel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PodcastNav(val id: String, val title: String, val image: String, val thumbnail: String) : Parcelable
