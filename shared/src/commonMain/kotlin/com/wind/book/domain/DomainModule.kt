package com.wind.book.domain

import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.domain.usecase.podcast.GetPodcastsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetBookListUseCase(get()) }
    factory { GetPodcastsUseCase(get()) }
}
