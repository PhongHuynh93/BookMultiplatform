package com.wind.book.domain

import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.domain.usecase.book.GetBookNameUseCase
import com.wind.book.domain.usecase.music.artist.GetArtistListByGenreUseCase
import com.wind.book.domain.usecase.music.genre.GetGenreListUseCase
import com.wind.book.domain.usecase.podcast.GetPodcastUseCase
import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailUseCase
import com.wind.book.domain.usecase.story.GetArticleListUseCase
import com.wind.book.domain.usecase.wallup.home.GetCategoryListUseCase
import com.wind.book.domain.usecase.wallup.home.GetColorListUseCase
import com.wind.book.domain.usecase.wallup.home.GetUnsplashPhotoListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetBookListUseCase(get()) }
    factory { GetPodcastUseCase(get()) }
    factory { GetArticleListUseCase(get()) }
    factory { GetPodcastDetailUseCase(get()) }
    factory { GetBookNameUseCase(get()) }
    factory { GetGenreListUseCase(get(), get()) }
    factory { GetArtistListByGenreUseCase(get(), get()) }
    factory { GetCategoryListUseCase(get(), get()) }
    factory { GetColorListUseCase(get(), get()) }
    factory { GetUnsplashPhotoListUseCase(get(), get()) }
}
