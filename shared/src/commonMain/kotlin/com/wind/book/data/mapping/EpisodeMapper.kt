package com.wind.book.data.mapping

import com.wind.book.data.model.dto.EpisodeDto
import com.wind.book.model.Episode

class EpisodeMapper : Mapper<EpisodeDto, Episode?> {
    override fun apply(input: EpisodeDto): Episode? {
        input.id ?: return null
        input.title ?: return null
        return Episode(
            input.id,
            input.title,
            input.description.orEmpty(),
            input.pubDateMs ?: 0,
            input.audio.orEmpty(),
            input.audioLengthSec ?: 0,
            input.listennotesUrl.orEmpty(),
            input.image.orEmpty(),
            input.thumbnail.orEmpty(),
            input.maybeAudioInvalid ?: false,
            input.listennotesEditUrl.orEmpty(),
            input.explicitContent ?: false,
            input.link.orEmpty(),
            input.guidFromRss.orEmpty()
        )
    }
}
