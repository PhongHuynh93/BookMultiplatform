package com.wind.book.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class BookName(val displayName: String, val encodedName: String) : Parcelable
