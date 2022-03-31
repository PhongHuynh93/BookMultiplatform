package com.wind.book.data.repository.wallup.home

import com.wind.book.data.mapping.wallup.UnsplashPhotoMapper
import com.wind.book.data.model.dto.wallup.UnsplashPhotoDto
import com.wind.book.data.util.BaseAPI
import com.wind.book.model.wallup.UnsplashPhoto
import io.ktor.client.request.parameter

interface HomeAPI {
    suspend fun getRandomPhotos(limit: Int): List<UnsplashPhoto>
}

internal class HomeAPIImpl : BaseAPI(), HomeAPI {

    private val unsplashPhotoMapper = UnsplashPhotoMapper()

    override val baseUrl: String
        get() = "https://api.unsplash.com"

    override suspend fun getRandomPhotos(limit: Int): List<UnsplashPhoto> {
        return doGet<List<UnsplashPhotoDto>> {
            apiPath("photos/random")
            parameter("client_id", "PolPMDn_Z8Qg7-OzpdV5TIOc9k8q6Kjhlp3HGR0RWto")
            parameter("count", limit)
        }.map {
            unsplashPhotoMapper.apply(it)
        }
    }
}
