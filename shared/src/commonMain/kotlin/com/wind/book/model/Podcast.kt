package com.wind.book.model

data class Podcast(
    val id: String,
    val title: String,
    val country: String,
    val description: String,
    val earliestPubDateMs: Long,
    val email: String,
    val explicitContent: Boolean,
    val extra: Extra,
    val genreIds: List<Int>,
    val image: String,
    val isClaimed: Boolean,
    val itunesId: Int,
    val language: String,
    val latestPubDateMs: Long,
    val listenScore: String,
    val listenScoreGlobalRank: String,
    val listennotesUrl: String,
    val lookingFor: LookingFor,
    val publisher: String,
    val rss: String,
    val thumbnail: String,
    val totalEpisodes: Int,
    val type: String,
    val website: String
)

data class LookingFor(
    val cohosts: Boolean = false,
    val crossPromotion: Boolean = false,
    val guests: Boolean = true,
    val sponsors: Boolean = false
)

data class Extra(
    val facebookHandle: String = "",
    val googleUrl: String = "",
    val instagramHandle: String = "",
    val linkedinUrl: String = "",
    val patreonHandle: String = "",
    val spotifyUrl: String = "",
    val twitterHandle: String = "",
    val url1: String = "",
    val url2: String = "",
    val url3: String = "",
    val wechatHandle: String = "",
    val youtubeUrl: String = ""
)