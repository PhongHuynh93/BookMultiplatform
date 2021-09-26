package com.wind.book.domain

import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.domain.usecase.podcast.GetPodcastUseCase
import com.wind.book.domain.usecase.story.GetArticleListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetBookListUseCase(get()) }
    factory { GetPodcastUseCase(get()) }
    factory { GetArticleListUseCase(get()) }
}
