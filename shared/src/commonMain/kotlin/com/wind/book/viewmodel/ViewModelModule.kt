package com.wind.book.viewmodel

import com.wind.book.viewModelDefinition
import com.wind.book.viewmodel.podcast.PodcastsViewModel
import com.wind.book.viewmodel.home.BookViewModel
import com.wind.book.viewmodel.iab.IABViewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModelDefinition { BookViewModel(get()) }
    viewModelDefinition { IABViewModel() }
    viewModelDefinition { PodcastsViewModel(get()) }
}
