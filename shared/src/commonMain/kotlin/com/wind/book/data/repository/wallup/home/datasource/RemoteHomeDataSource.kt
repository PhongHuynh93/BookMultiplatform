package com.wind.book.data.repository.wallup.home.datasource

import com.wind.book.data.repository.wallup.home.HomeAPI
import com.wind.book.model.wallup.UnsplashPhoto

interface RemoteHomeDataSource {
    suspend fun getRandomPhotos(limit: Int): List<UnsplashPhoto>
}

internal class RemoteHomeDataSourceImpl(private val api: HomeAPI) : RemoteHomeDataSource {

    override suspend fun getRandomPhotos(limit: Int): List<UnsplashPhoto> {
        return api.getRandomPhotos(limit)
    }
}
