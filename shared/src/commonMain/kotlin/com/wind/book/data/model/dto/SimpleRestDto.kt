package com.wind.book.data.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SimpleRestDto<T>(
    @SerialName("copyright")
    val copyright: String? = null,
    @SerialName("last_modified")
    val lastModified: String? = null,
    @SerialName("num_results")
    val numResults: Int? = null,
    @SerialName("results")
    val results: T? = null,
    @SerialName("status")
    val status: String? = null
)
