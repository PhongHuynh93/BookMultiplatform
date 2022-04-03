package com.wind.book.viewmodel.wallup.home

import com.wind.book.domain.usecase.wallup.home.GetCategoryListParam
import com.wind.book.domain.usecase.wallup.home.GetCategoryListUseCase
import com.wind.book.domain.usecase.wallup.home.GetColorListParam
import com.wind.book.domain.usecase.wallup.home.GetColorListUseCase
import com.wind.book.domain.usecase.wallup.home.GetUnsplashPhotoListParam
import com.wind.book.domain.usecase.wallup.home.GetUnsplashPhotoListUseCase
import com.wind.book.log
import com.wind.book.model.wallup.ColorItem
import com.wind.book.model.wallup.UnsplashPhoto
import com.wind.book.model.wallup.WallupCategory
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadingEvent
import com.wind.book.viewmodel.LoadingVM
import com.wind.book.viewmodel.model.wallup.Home
import kotlinx.coroutines.launch

interface HomeEvent : LoadingEvent {
    fun onClickPhoto(photo: UnsplashPhoto)
    fun onClickColor(colorItem: ColorItem)
    fun onClickCategory(wallupCategory: WallupCategory)
    fun onShake()
}

sealed class HomeEffect : BaseEffect() {
    object ScrollToTop : HomeEffect()
}

class HomeViewModel(
    private val getColorListUseCase: GetColorListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getUnsplashPhotoListUseCase: GetUnsplashPhotoListUseCase
) : LoadingVM<Home, HomeEffect>(), HomeEvent {

    override val event = this as HomeEvent

    init {
        loadData()
    }

    override fun onClickPhoto(photo: UnsplashPhoto) {
    }

    override fun onClickColor(colorItem: ColorItem) {
    }

    override fun onClickCategory(wallupCategory: WallupCategory) {
    }

    override fun onShake() {
        clientScope.launch {
            _effect.emit(HomeEffect.ScrollToTop)
        }
    }

    override suspend fun apiCall(): Result<List<Home>> {
        return try {
            Result.success(getColors() + getCategories() + getRandomPhotos())
        } catch (e: Exception) {
            log.e { "Crash ${e.stackTraceToString()}" }
            Result.failure(e)
        }
    }

    private suspend fun getColors(): List<Home> {
        return getColorListUseCase(GetColorListParam()).map {
            listOf(Home.ColorList(it))
        }.getOrThrow()
    }

    private suspend fun getCategories(): List<Home> {
        return getCategoryListUseCase(GetCategoryListParam()).map {
            listOf(Home.CategoryList(it))
        }.getOrThrow()
    }

    private suspend fun getRandomPhotos(): List<Home> {
        return getUnsplashPhotoListUseCase(GetUnsplashPhotoListParam(10)).map {
            listOf(Home.RandomPhotoList(it))
        }.getOrThrow()
    }
}
