package com.wind.book.data.repository.wallup.home

import com.wind.book.data.repository.wallup.home.datasource.RemoteHomeDataSource
import com.wind.book.model.wallup.Category
import com.wind.book.model.wallup.ColorItem
import com.wind.book.model.wallup.UnsplashPhoto

interface HomeRepository {
    suspend fun getColorList(): List<ColorItem>
    suspend fun getCategoryList(): List<Category>
    suspend fun getRandomPhotos(limit: Int): List<UnsplashPhoto>
}

internal class HomeRepositoryImpl(
    private val remoteSource: RemoteHomeDataSource,
) : HomeRepository {

    override suspend fun getColorList(): List<ColorItem> {
        return listOf(
            ColorItem("#FEB6B7", "Pink"),
            ColorItem("#F6F1F2", "White"),
            ColorItem("#6142E0", "Purple"),
            ColorItem("#34568B", "Classic Blue"),
            ColorItem("#FF6F61", "Living Coral"),
            ColorItem("#6B5B95", "Ultra Violet"),
            ColorItem("#88B04B", "Greenery"),
            ColorItem("#F7CAC9", "Rose Quartz"),
            ColorItem("#92A8D1", "Serenity"),
            ColorItem("#955251", "Marsala"),
            ColorItem("#B565A7", "Radiand Orchid"),
            ColorItem("#009B77", "Emerald"),
            ColorItem("#DD4124", "Tangerine Tango"),
            ColorItem("#D65076", "Honeysucle"),
            ColorItem("#45B8AC", "Turquoise"),
            ColorItem("#EFC050", "Mimosa"),
            ColorItem("#5B5EA6", "Blue Izis"),
            ColorItem("#9B2335", "Chili pepper"),
            ColorItem("#DFCFBE", "Sand Dollar"),
            ColorItem("#55B4B0", "Blue Turquoise"),
            ColorItem("#E15D44", "Tigerlily"),
            ColorItem("#7FCDCD", "Aqua Sky"),
            ColorItem("#BC243C", "True Red"),
            ColorItem("#C3447A", "Fuchsia Rose"),
            ColorItem("#98B4D4", "Cerulean Blue"),
        )
    }

    override suspend fun getCategoryList(): List<Category> {
        return Category.values().toList()
    }

    override suspend fun getRandomPhotos(limit: Int): List<UnsplashPhoto> {
        return remoteSource.getRandomPhotos(limit)
    }
}
