package com.wind.book.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Section response dto
@Serializable
data class SectionResDto(
    @SerialName("copyright")
    val copyright: String? = null,
    @SerialName("last_updated")
    val lastUpdated: String? = null,
    @SerialName("num_results")
    val numResults: Int? = null,
    @SerialName("results")
    val results: List<ArticleDto?>? = null,
    @SerialName("status")
    val status: String? = null
)

@Serializable
data class ArticleDto(
    @SerialName("section")
    val section: String? = null,
    @SerialName("subsection")
    val subSection: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("abstract")
    val abstract: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("uri")
    val uri: String? = null,
    @SerialName("by_line")
    val byLine: Int? = null,
    @SerialName("item_type")
    val itemType: String? = null,
    @SerialName("updated_date")
    val updatedDate: String? = null,
    @SerialName("created_date")
    val createdDate: String? = null,
    @SerialName("published_date")
    val publishedDate: String? = null,
    @SerialName("material_type_facet")
    val materialTypeFacet: String? = null,
    @SerialName("kicker")
    val kicker: String? = null,
    @SerialName("multimedia")
    val multimedia: List<MultimediaDto?>? = null,
    @SerialName("short_url")
    val shortUrl: String? = null
)

@Serializable
data class MultimediaDto(
    @SerialName("url")
    val url: String? = null,
    @SerialName("format")
    val format: String? = null,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("width")
    val width: Int? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("subtype")
    val subtype: String? = null,
    @SerialName("caption")
    val caption: String? = null,
    @SerialName("copyright")
    val copyright: String? = null
)
