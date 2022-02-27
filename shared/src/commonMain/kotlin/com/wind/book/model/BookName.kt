package com.wind.book.model

import com.wind.book.Parcelable
import com.wind.book.Parcelize

@Parcelize
data class BookName(val displayName: String, val encodedName: String) : Parcelable
