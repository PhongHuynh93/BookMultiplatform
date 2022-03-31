package com.wind.book.domain.usecase.wallup.home

import com.wind.book.data.repository.wallup.home.HomeRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.wallup.ColorItem
import kotlinx.coroutines.CoroutineDispatcher

class GetColorListParam

open class GetColorListUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : UseCase<GetColorListParam, List<ColorItem>>(dispatcher) {
    override suspend fun execute(parameters: GetColorListParam): List<ColorItem> {
        return repository.getColorList()
    }
}
