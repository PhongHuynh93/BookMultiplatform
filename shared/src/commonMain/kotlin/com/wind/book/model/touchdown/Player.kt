package com.wind.book.model.touchdown

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val image: String
)
