package com.wind.book.domain.usecase.music.genre

import com.wind.book.data.repository.music.genre.GenreRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.music.Genre
import com.wind.book.platformCoroutineDispatcher

class GetGenreListParam(val index: Int, val limit: Int)
open class GetGenreListUseCase constructor(
    private val genreRepository: GenreRepository
) : UseCase<GetGenreListParam, List<Genre>>(platformCoroutineDispatcher) {
    override suspend fun execute(parameters: GetGenreListParam): List<Genre> {
        return genreRepository.getGenreList(parameters.index, parameters.limit)
    }
}
