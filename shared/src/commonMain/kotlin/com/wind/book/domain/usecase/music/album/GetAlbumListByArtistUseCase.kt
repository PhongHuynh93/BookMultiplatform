package com.wind.book.domain.usecase.music.album

import com.wind.book.data.repository.music.genre.GenreRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.music.Album
import kotlinx.coroutines.CoroutineDispatcher

class GetAlbumListByArtistParam(val id: String, val index: Int, val limit: Int)
class GetAlbumListByArtistUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val genreRepository: GenreRepository,
) : UseCase<GetAlbumListByArtistParam, List<Album>>(dispatcher) {
    override suspend fun execute(parameters: GetAlbumListByArtistParam): List<Album> {
        return genreRepository.getArtistAlbumList(parameters.id, parameters.index, parameters.limit)
    }
}
