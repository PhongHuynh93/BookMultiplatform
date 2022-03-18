package com.wind.book.viewmodel.music.artist

import com.wind.book.domain.usecase.music.artist.GetArtistListByGenreParam
import com.wind.book.domain.usecase.music.artist.GetArtistListByGenreUseCase
import com.wind.book.log
import com.wind.book.model.music.Artist
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import kotlinx.coroutines.launch

interface ArtistEvent : LoadMoreEvent {
    fun onClickArtist(artist: Artist)
    fun setGenreId(genreId: String)
}

sealed class ArtistEffect : BaseEffect() {
    class NavToAlbum(val artist: Artist) : ArtistEffect()
}

class ArtistViewModel(
    private val getArtistListByGenreUseCase: GetArtistListByGenreUseCase
) : LoadMoreVM<Artist, ArtistEffect>(), ArtistEvent {
    override val event = this as ArtistEvent
    private var genreId: String = ""

    override suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<Artist>> {
        return getArtistListByGenreUseCase(
            GetArtistListByGenreParam(
                genreId,
                currentPage,
                pageSize
            )
        )
    }

    override fun onClickArtist(artist: Artist) {
        clientScope.launch {
            _effect.emit(ArtistEffect.NavToAlbum(artist))
        }
    }

    override fun setGenreId(genreId: String) {
        log.d { "genreId $genreId ArtistViewModel hashcode ${hashCode()}" }
        this.genreId = genreId
        refresh()
    }
}
