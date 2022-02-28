package com.wind.book.data.model.dto.music

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicDto<out T>(
    @SerialName("data")
    val data: List<T?>? = null,
    @SerialName("next")
    val next: String? = null,
    @SerialName("total")
    val total: Int? = null
)
