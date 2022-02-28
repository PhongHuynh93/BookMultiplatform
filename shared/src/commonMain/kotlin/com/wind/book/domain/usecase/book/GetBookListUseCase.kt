package com.wind.book.domain.usecase.book

import com.wind.book.data.repository.book.BookRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.Book
import com.wind.book.platformCoroutineDispatcher

class GetBookListParam(val currentPage: Int, val listName: String)
class GetBookListUseCase constructor(
    private val bookRepository: BookRepository
) : UseCase<GetBookListParam, List<Book>>(platformCoroutineDispatcher) {
    override suspend fun execute(parameters: GetBookListParam): List<Book> {
        return bookRepository.getBookList(parameters.currentPage, parameters.listName)
    }
}
