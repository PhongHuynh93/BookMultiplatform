package com.wind.book.data.util

import com.wind.book.model.wallup.ColorItem
import com.wind.book.model.wallup.PhotoDetail
import com.wind.book.model.wallup.RelatedCollections
import com.wind.book.model.wallup.UnsplashPhoto
import com.wind.book.model.wallup.UserDetail
import com.wind.book.model.wallup.WallupCategory

object FakeWallupData {

    val unsplashPhoto = UnsplashPhoto(
        id = "",
        createdAt = "",
        blurHash = "",
        color = "",
        thumbnail = "",
        smallImage = "",
        fullImage = "",
        views = 1,
        downloads = 1,
        likes = 1,
        sourceUrl = "",
        photoDetail = PhotoDetail(
            width = 100,
            height = 100,
            _cameraName = "",
            _aperture = "",
            _focalLength = "",
            _exposureTime = "",
            _iso = "",
        ),
        userDetail = UserDetail(
            name = "",
            image = "",
            instaUserName = "",
            twitterUserName = "",
            unsplashProfile = "",
        ),
        relatedCollections = RelatedCollections(
            collections = emptyList(),
            total = 1
        ),
        isBookmarked = false,
    )

    val colorItemList = listOf(
        ColorItem("#FEB6B7", "Pink"),
        ColorItem("#F6F1F2", "White"),
        ColorItem("#6142E0", "Purple")
    )

    val categoryList = WallupCategory.values().toList()
}
