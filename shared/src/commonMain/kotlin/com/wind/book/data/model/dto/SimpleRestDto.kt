package com.wind.book.data.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SimpleRestDto(
    @SerialName("status")
    var status: String? = null
)
