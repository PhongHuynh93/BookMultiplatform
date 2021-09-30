package com.wind.book.domain.usecase.book

import com.wind.book.data.repository.book.BookRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.BookName
import com.wind.book.platformCoroutineDispatcher

class GetBookNameParam()
class GetBookNameUseCase constructor(
    private val bookRepository: BookRepository
) : UseCase<GetBookNameParam, List<BookName>>(platformCoroutineDispatcher) {
    override suspend fun execute(parameters: GetBookNameParam): List<BookName> {
        return bookRepository.getNames()
    }
}
