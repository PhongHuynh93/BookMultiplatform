package com.wind.book.viewmodel.music.genre

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.wind.book.data.util.FakeData
import com.wind.book.domain.usecase.music.genre.GetGenreListParam
import com.wind.book.domain.usecase.music.genre.GetGenreListUseCase
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.BaseVMUnitTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GenreViewModelTest : BaseVMUnitTest() {

    private fun createVM(
        getGenreListUseCase: GetGenreListUseCase = object : GetGenreListUseCase(mock {}) {
            override suspend fun execute(parameters: GetGenreListParam): List<Genre> {
                return FakeData.genreList
            }
        }
    ) = GenreViewModel(
        getGenreListUseCase = getGenreListUseCase
    )

    @Test
    fun `when click genre then nav to artist page`() = runTest {
        val vm = createVM()
        val job = launch {
            vm.genreEffect.collectLatest {
                when (it) {
                    is GenreEffect.NavToArtist -> {
                        assertThat(it.genre).isEqualTo(FakeData.genre)
                    }
                    else -> {
                        throw UnsupportedOperationException()
                    }
                }
            }
        }
        job.cancel()
        vm.onClickGenre(FakeData.genre)
    }
}
