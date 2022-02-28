package com.wind.book.usecase.music.artist

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.wind.book.data.repository.music.genre.GenreRepository
import com.wind.book.data.util.FakeData
import com.wind.book.domain.usecase.music.artist.GetArtistListByGenreParam
import com.wind.book.domain.usecase.music.artist.GetArtistListByGenreUseCase
import com.wind.book.viewmodel.BaseUnitTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetArtistListByGenreUseCaseTest : BaseUnitTest() {

    @Test
    fun `when called then verify the result`() = runTest(testDispatcher) {
        val repo: GenreRepository = mock {
            onBlocking { getArtistList("1", 1, 1) }.thenReturn(FakeData.artistList)
        }
        GetArtistListByGenreUseCase(testDispatcher, repo)(
            GetArtistListByGenreParam(
                "1",
                1,
                1
            )
        ).let {
            Truth.assertThat(it.getOrThrow()).isEqualTo(FakeData.artistList)
        }
    }
}
