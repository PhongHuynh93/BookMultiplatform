package org.tivi.sharedTivi.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Episode(
    override val id: Long = 0,
    val seasonId: Long,
    override val traktId: Int? = null,
    override val tmdbId: Int? = null,
    val title: String? = null,
    val summary: String? = null,
    val number: Int? = null,
    val firstAired: Instant? = null,
    val traktRating: Float? = null,
    val traktRatingVotes: Int? = null,
    val tmdbBackdropPath: String? = null
) : TiviEntity, TraktIdEntity, TmdbIdEntity {

    companion object {
        val EMPTY = Episode(seasonId = 0)
    }

    val isAired by lazy { firstAired?.let { it < Clock.System.now() } ?: false }
}
