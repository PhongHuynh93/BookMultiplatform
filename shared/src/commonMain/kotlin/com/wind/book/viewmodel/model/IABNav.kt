package com.wind.book.viewmodel.model

import com.wind.book.Parcelable
import com.wind.book.Parcelize

@Parcelize
class IABNav(val title: String, val url: String) : Parcelable {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        title = "",
        url = "",
    )
}
