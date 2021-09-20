package com.wind.book.viewmodel

import com.wind.book.viewmodel.home.BookViewModel
import org.koin.dsl.module
import com.wind.book.viewModelDefinition

val viewmodelModule = module {
    viewModelDefinition { BookViewModel(get()) }
}
