package com.wind.book.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("pub_date_ms")
    val pubDateMs: Long? = null,
    @SerialName("audio")
    val audio: String? = null,
    @SerialName("audio_length_sec")
    val audioLengthSec: Int? = null,
    @SerialName("listennotes_url")
    val listennotesUrl: String? = null,
    @SerialName("image")
    val image: String? = null,
    @SerialName("thumbnail")
    val thumbnail: String? = null,
    @SerialName("maybe_audio_invalid")
    val maybeAudioInvalid: Boolean? = null,
    @SerialName("listennotes_edit_url")
    val listennotesEditUrl: String? = null,
    @SerialName("explicit_content")
    val explicitContent: Boolean? = null,
    @SerialName("link")
    val link: String? = null,
    @SerialName("guid_from_rss")
    val guidFromRss: String? = null
)
