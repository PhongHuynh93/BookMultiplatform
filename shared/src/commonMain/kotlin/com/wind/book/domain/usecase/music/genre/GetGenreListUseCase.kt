package com.wind.book.domain.usecase.music.genre

import com.wind.book.data.repository.music.genre.GenreRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.music.Genre
import kotlinx.coroutines.CoroutineDispatcher

class GetGenreListParam(val index: Int, val limit: Int)
open class GetGenreListUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val genreRepository: GenreRepository,
) : UseCase<GetGenreListParam, List<Genre>>(dispatcher) {
    override suspend fun execute(parameters: GetGenreListParam): List<Genre> {
        return genreRepository.getGenreList(parameters.index, parameters.limit)
    }
}
