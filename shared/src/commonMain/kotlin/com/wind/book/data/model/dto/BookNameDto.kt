package com.wind.book.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookNameListDto(
    @SerialName("copyright")
    val copyright: String? = null,
    @SerialName("num_results")
    val numResults: Int? = null,
    @SerialName("results")
    val results: List<BookNameDto?>? = null,
    @SerialName("status")
    val status: String? = null
)

@Serializable
data class BookNameDto(
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("list_name")
    val listName: String? = null,
    @SerialName("list_name_encoded")
    val listNameEncoded: String? = null,
    @SerialName("newest_published_date")
    val newestPublishedDate: String? = null,
    @SerialName("oldest_published_date")
    val oldestPublishedDate: String? = null,
    @SerialName("updated")
    val updated: String? = null
)
