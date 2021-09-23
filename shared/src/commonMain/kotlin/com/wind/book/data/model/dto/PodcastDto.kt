package com.wind.book.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PodcastListDto(
    @SerialName("podcasts")
    val podcastDtos: List<PodcastDto>? = null,
)

@Serializable
data class PodcastDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("earliest_pub_date_ms")
    val earliestPubDateMs: Long? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("explicit_content")
    val explicitContent: Boolean? = null,
    @SerialName("extra")
    val extraDto: ExtraDto? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("image")
    val image: String? = null,
    @SerialName("is_claimed")
    val isClaimed: Boolean? = null,
    @SerialName("itunes_id")
    val itunesId: Int,
    @SerialName("language")
    val language: String? = null,
    @SerialName("latest_pub_date_ms")
    val latestPubDateMs: Long? = null,
    @SerialName("listen_score")
    val listenScore: String? = null,
    @SerialName("listen_score_global_rank")
    val listenScoreGlobalRank: String? = null,
    @SerialName("listennotes_url")
    val listennotesUrl: String? = null,
    @SerialName("looking_for")
    val lookingForDto: LookingForDto? = null,
    @SerialName("publisher")
    val publisher: String? = null,
    @SerialName("rss")
    val rss: String? = null,
    @SerialName("thumbnail")
    val thumbnail: String? = null,
    @SerialName("total_episodes")
    val totalEpisodes: Int? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("website")
    val website: String? = null,
)

@Serializable
data class LookingForDto(
    @SerialName("cohosts")
    val cohosts: Boolean? = null,
    @SerialName("cross_promotion")
    val crossPromotion: Boolean? = null,
    @SerialName("guests")
    val guests: Boolean? = null,
    @SerialName("sponsors")
    val sponsors: Boolean
)

@Serializable
data class ExtraDto(
    @SerialName("facebook_handle")
    val facebookHandle: String? = null,
    @SerialName("google_url")
    val googleUrl: String? = null,
    @SerialName("instagram_handle")
    val instagramHandle: String? = null,
    @SerialName("linkedin_url")
    val linkedinUrl: String? = null,
    @SerialName("patreon_handle")
    val patreonHandle: String? = null,
    @SerialName("spotify_url")
    val spotifyUrl: String? = null,
    @SerialName("twitter_handle")
    val twitterHandle: String? = null,
    @SerialName("url1")
    val url1: String? = null,
    @SerialName("url2")
    val url2: String? = null,
    @SerialName("url3")
    val url3: String? = null,
    @SerialName("wechat_handle")
    val wechatHandle: String? = null,
    @SerialName("youtube_url")
    val youtubeUrl: String? = null
)
