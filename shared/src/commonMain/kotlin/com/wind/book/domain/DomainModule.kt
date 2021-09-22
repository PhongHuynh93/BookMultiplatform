package com.wind.book.domain

import com.wind.book.domain.usecase.book.GetBookListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetBookListUseCase(get()) }
}
