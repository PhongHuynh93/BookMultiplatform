package com.wind.book.domain.usecase.wallup.home

import com.wind.book.data.repository.wallup.home.HomeRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.wallup.Category
import kotlinx.coroutines.CoroutineDispatcher

class GetCategoryListParam

open class GetCategoryListUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : UseCase<GetCategoryListParam, List<Category>>(dispatcher) {
    override suspend fun execute(parameters: GetCategoryListParam): List<Category> {
        return repository.getCategoryList()
    }
}
