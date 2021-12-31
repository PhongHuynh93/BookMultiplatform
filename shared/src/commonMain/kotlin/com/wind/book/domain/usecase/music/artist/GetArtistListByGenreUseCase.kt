package com.wind.book.domain.usecase.music.artist

import com.wind.book.data.repository.music.genre.GenreRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.music.Artist
import kotlinx.coroutines.CoroutineDispatcher

class GetArtistListByGenreParam(val genreId: String, val index: Int, val limit: Int)
class GetArtistListByGenreUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val genreRepository: GenreRepository
) : UseCase<GetArtistListByGenreParam, List<Artist>>(dispatcher) {
    override suspend fun execute(parameters: GetArtistListByGenreParam): List<Artist> {
        return genreRepository.getArtistList(parameters.genreId, parameters.index, parameters.limit)
    }
}
