package com.wind.book.data.mapping

import com.wind.book.data.model.dto.ExtraDto
import com.wind.book.data.model.dto.PodcastDto
import com.wind.book.model.Podcast

class PodcastMapper : Mapper<PodcastDto, Podcast?> {
    private val extraMapper = ExtraMapper()

    override fun apply(input: PodcastDto): Podcast? = input.run {
        input.id ?: return null
        input.title ?: return null
        input.thumbnail ?: return null
        Podcast(
            input.id.orEmpty(),
            input.title.orEmpty(),
            input.publisher.orEmpty(),
            input.image.orEmpty(),
            input.thumbnail.orEmpty(),
            input.listennotesUrl.orEmpty(),
            input.listenScore.orEmpty(),
            input.listenScoreGlobalRank.orEmpty(),
            input.totalEpisodes ?: 0,
            input.explicitContent ?: false,
            input.description.orEmpty(),
            input.itunesId ?: 0,
            input.rss.orEmpty(),
            input.latestPubDateMs ?: 0,
            input.earliestPubDateMs ?: 0,
            input.language.orEmpty(),
            input.country.orEmpty(),
            input.website.orEmpty(),
            input.extraDto?.let { extraMapper.apply(it) } ?: Podcast.Extra(),
            input.isClaimed ?: false,
            input.email.orEmpty(),
            input.type.orEmpty(),
            input.genreIds ?: emptyList()
        )
    }

    class ExtraMapper : Mapper<ExtraDto, Podcast.Extra> {
        override fun apply(input: ExtraDto) = Podcast.Extra(
            input.facebookHandle.orEmpty(),
            input.googleUrl.orEmpty(),
            input.instagramHandle.orEmpty(),
            input.linkedinUrl.orEmpty(),
            input.patreonHandle.orEmpty(),
            input.spotifyUrl.orEmpty(),
            input.twitterHandle.orEmpty(),
            input.url1.orEmpty(),
            input.url2.orEmpty(),
            input.url3.orEmpty(),
            input.wechatHandle.orEmpty(),
            input.youtubeUrl.orEmpty()
        )
    }
}
