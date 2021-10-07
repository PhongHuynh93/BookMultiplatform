package com.wind.book.viewmodel.music.genre

import com.wind.book.domain.usecase.music.genre.GetGenreListParam
import com.wind.book.domain.usecase.music.genre.GetGenreListUseCase
import com.wind.book.log
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadingEvent
import com.wind.book.viewmodel.LoadingVM
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface GenreEvent : LoadingEvent {
    fun onClickGenre(genre: Genre)
}

sealed class GenreEffect : BaseEffect() {
    class LoadingEffect(val loadingEffect: com.wind.book.viewmodel.LoadingEffect) : GenreEffect()
}

class GenreViewModel(
    private val getGenreListUseCase: GetGenreListUseCase
) : LoadingVM<Genre>(), GenreEvent {

    private val _genreEffect = MutableSharedFlow<GenreEffect>()
    val genreEffect = _genreEffect.asSharedFlow()
    override val event = this as GenreEvent

    init {
        // capture the base effect and emit again
        clientScope.launch {
            effect.collectLatest {
                _genreEffect.emit(GenreEffect.LoadingEffect(it))
            }
        }
    }

    override suspend fun apiCall(): Result<List<Genre>> {
        log.d { "genrelsit use case $getGenreListUseCase" }
        return getGenreListUseCase(GetGenreListParam())
    }

    override fun onClickGenre(genre: Genre) {
        TODO("Not yet implemented")
    }
}
