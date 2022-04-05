package com.wind.book.model.touchdown

import kotlinx.serialization.Serializable

@Serializable
data class TouchDownCategory(
    val id: Int,
    val name: String,
    val image: String
)
