package com.wind.book.domain.usecase.wallup.home

import com.wind.book.data.repository.wallup.home.HomeRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.wallup.UnsplashPhoto
import kotlinx.coroutines.CoroutineDispatcher

class GetUnsplashPhotoListParam(val limit: Int)

open class GetUnsplashPhotoListUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : UseCase<GetUnsplashPhotoListParam, List<UnsplashPhoto>>(dispatcher) {
    override suspend fun execute(parameters: GetUnsplashPhotoListParam): List<UnsplashPhoto> {
        return repository.getRandomPhotos(parameters.limit)
    }
}
