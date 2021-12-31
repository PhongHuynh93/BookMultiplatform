package com.wind.book.usecase.music.genre

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.wind.book.data.repository.music.genre.GenreRepository
import com.wind.book.data.util.FakeData
import com.wind.book.domain.usecase.music.genre.GetGenreListParam
import com.wind.book.domain.usecase.music.genre.GetGenreListUseCase
import com.wind.book.viewmodel.BaseUnitTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetGenreListUseCaseTest : BaseUnitTest() {

    @Test
    fun `when called then verify the result`() = runTest(testDispatcher) {
        val repo: GenreRepository = mock {
            onBlocking { getGenreList(1, 1) }.thenReturn(FakeData.genreList)
        }
        GetGenreListUseCase(testDispatcher, repo)(GetGenreListParam(1, 1)).let {
            Truth.assertThat(it.getOrThrow()).isEqualTo(FakeData.genreList)
        }
    }
}
