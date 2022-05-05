package org.tivi.sharedTivi.model

interface TiviEntity {
    val id: Long
}

interface TraktIdEntity {
    val traktId: Int?
}

interface TmdbIdEntity {
    val tmdbId: Int?
}
