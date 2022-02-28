package com.wind.book.model

import com.wind.book.Parcelable
import com.wind.book.Parcelize

@Parcelize
data class Podcast(
    override val id: String,
    val title: String,
    val publisher: String,
    val image: String,
    val thumbnail: String,
    val listennotesUrl: String = "",
    val listenScore: String = "",
    val listenScoreGlobalRank: String = "",
    val totalEpisodes: Int,
    val explicitContent: Boolean = false,
    val description: String,
    val itunesId: Int = 0,
    val rss: String = "",
    val latestPubDateMs: Long = 0,
    val earliestPubDateMs: Long = 0,
    val language: String = "",
    val country: String = "",
    val website: String = "",
    val extra: Extra? = null,
    val isClaimed: Boolean = false,
    val email: String = "",
    val type: String = "",
    val genreIds: List<Int> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val nextEpisodePubDate: Long = 0
) : Parcelable, Identifiable {

    @Parcelize
    data class Extra(
        val twitterHandle: String = "",
        val facebookHandle: String = "",
        val instagramHandle: String = "",
        val wechatHandle: String = "",
        val patreonHandle: String = "",
        val youtubeUrl: String = "",
        val linkedinUrl: String = "",
        val spotifyUrl: String = "",
        val googleUrl: String = "",
        val url1: String = "",
        val url2: String = "",
        val url3: String = ""
    ) : Parcelable
}
