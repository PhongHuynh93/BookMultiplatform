package com.wind.book.viewmodel.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
class IABNav(val title: String, val url: String) : Parcelable