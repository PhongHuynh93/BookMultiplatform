package com.wind.book.data.mapping

import com.wind.book.data.model.dto.ExtraDto
import com.wind.book.data.model.dto.LookingForDto
import com.wind.book.data.model.dto.PodcastDto
import com.wind.book.model.Extra
import com.wind.book.model.LookingFor
import com.wind.book.model.Podcast

class PodcastMapper : Mapper<PodcastDto, Podcast?> {
    private val extraMapper = ExtraMapper()
    private val lookingForMapper = LookingForMapper()

    override fun apply(input: PodcastDto): Podcast? = input.run {
        input.id ?: return null
        input.title ?: return null
        input.thumbnail ?: return null
        Podcast(
            input.id.orEmpty(),
            input.title.orEmpty(),
            input.country.orEmpty(),
            input.description.orEmpty(),
            input.earliestPubDateMs ?: 0,
            input.email.orEmpty(),
            input.explicitContent ?: false,
            input.extraDto?.let { extraMapper.apply(it) } ?: Extra(),
            input.genreIds,
            input.image.orEmpty(),
            input.isClaimed ?: false,
            input.itunesId,
            input.language.orEmpty(),
            input.latestPubDateMs ?: 0,
            input.listenScore.orEmpty(),
            input.listenScoreGlobalRank.orEmpty(),
            input.listennotesUrl.orEmpty(),
            input.lookingForDto?.let { lookingForMapper.apply(it) } ?: LookingFor(),
            input.publisher.orEmpty(),
            input.rss.orEmpty(),
            input.thumbnail.orEmpty(),
            input.totalEpisodes ?: 0,
            input.type.orEmpty(),
            input.website ?: ""
        )
    }
}

class LookingForMapper : Mapper<LookingForDto, LookingFor> {
    override fun apply(input: LookingForDto) = LookingFor(
        input.cohosts ?: false,
        input.crossPromotion ?: false,
        input.guests ?: false,
        input.sponsors
    )
}

class ExtraMapper : Mapper<ExtraDto, Extra> {
    override fun apply(input: ExtraDto) = Extra(
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
