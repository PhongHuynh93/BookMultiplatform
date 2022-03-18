package com.wind.book.viewmodel.music.album

import com.wind.book.domain.usecase.music.album.GetAlbumListByArtistParam
import com.wind.book.domain.usecase.music.album.GetAlbumListByArtistUseCase
import com.wind.book.log
import com.wind.book.model.music.Album
import com.wind.book.model.music.Artist
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM

interface AlbumEvent : LoadMoreEvent {
    fun setArtist(artist: Artist)
}

sealed class AlbumEffect : BaseEffect()

class AlbumViewModel(
    private val getAlbumListByArtistUseCase: GetAlbumListByArtistUseCase
) : LoadMoreVM<Album, AlbumEffect>(), AlbumEvent {

    private lateinit var artist: Artist

    override val event: AlbumEvent
        get() = this

    override suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<Album>> {
        return getAlbumListByArtistUseCase(
            GetAlbumListByArtistParam(
                artist.id,
                currentPage,
                pageSize
            )
        )
    }

    override fun setArtist(artist: Artist) {
        log.d { "artistId $artist" }
        this.artist = artist
        refresh()
    }
}
