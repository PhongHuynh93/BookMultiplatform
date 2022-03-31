package com.wind.book.data.util

import com.wind.book.model.wallup.PhotoDetail
import com.wind.book.model.wallup.RelatedCollections
import com.wind.book.model.wallup.UnsplashPhoto
import com.wind.book.model.wallup.UserDetail

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
}
