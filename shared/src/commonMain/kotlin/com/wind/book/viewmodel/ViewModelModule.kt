package com.wind.book.viewmodel

import com.wind.book.viewModelDefinition
import com.wind.book.viewmodel.book.BookPagerViewModel
import com.wind.book.viewmodel.book.BookViewModel
import com.wind.book.viewmodel.home.StoryViewModel
import com.wind.book.viewmodel.iab.IABViewModel
import com.wind.book.viewmodel.music.genre.GenreViewModel
import com.wind.book.viewmodel.podcast.PodcastViewModel
import com.wind.book.viewmodel.podcast_detail.PodcastDetailViewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModelDefinition { BookViewModel(get()) }
    viewModelDefinition { IABViewModel() }
    viewModelDefinition { PodcastViewModel(get()) }
    viewModelDefinition { StoryViewModel(get()) }
    viewModelDefinition { PodcastDetailViewModel(get()) }
    viewModelDefinition { BookPagerViewModel(get()) }
    viewModelDefinition { GenreViewModel(get()) }
}
