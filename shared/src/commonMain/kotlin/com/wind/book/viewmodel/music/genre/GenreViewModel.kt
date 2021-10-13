package com.wind.book.viewmodel.music.genre

import androidx.lifecycle.viewModelScope
import com.wind.book.domain.usecase.music.genre.GetGenreListParam
import com.wind.book.domain.usecase.music.genre.GetGenreListUseCase
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface GenreEvent : LoadMoreEvent {
    fun onClickGenre(genre: Genre)
}

sealed class GenreEffect : BaseEffect() {
    class LoadMoreEffect(val loadMoreEffect: com.wind.book.viewmodel.LoadMoreEffect) : GenreEffect()
    class NavToArtist(val genre: Genre) : GenreEffect()
}

class GenreViewModel(
    private val getGenreListUseCase: GetGenreListUseCase
) : LoadMoreVM<Genre>(), GenreEvent {

    private val _genreEffect = MutableSharedFlow<GenreEffect>()
    val genreEffect = _genreEffect.asSharedFlow()
    override val event = this as GenreEvent

    init {
        loadMore()
        // capture the base effect and emit again
        clientScope.launch {
            effect.collectLatest {
                _genreEffect.emit(GenreEffect.LoadMoreEffect(it))
            }
        }
    }

    override fun onClickGenre(genre: Genre) {
        viewModelScope.launch {
            _genreEffect.emit(GenreEffect.NavToArtist(genre))
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
