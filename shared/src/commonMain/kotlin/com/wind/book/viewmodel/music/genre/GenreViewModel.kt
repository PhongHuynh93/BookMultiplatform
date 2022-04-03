package com.wind.book.viewmodel.music.genre

import com.wind.book.domain.usecase.music.genre.GetGenreListParam
import com.wind.book.domain.usecase.music.genre.GetGenreListUseCase
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import kotlinx.coroutines.launch

interface GenreEvent : LoadMoreEvent {
    fun onClickGenre(genre: Genre)
}

sealed class GenreEffect : BaseEffect() {
    class NavToArtist(val genre: Genre) : GenreEffect()
}

class GenreViewModel(
    private val getGenreListUseCase: GetGenreListUseCase
) : LoadMoreVM<Genre, GenreEffect>(), GenreEvent {

    override val event = this as GenreEvent

    init {
        loadData()
    }

    override fun onClickGenre(genre: Genre) {
        clientScope.launch {
            _effect.emit(GenreEffect.NavToArtist(genre))
        }
    }

    override suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<Genre>> {
        return getGenreListUseCase(GetGenreListParam(currentPage, pageSize))
    }
}
