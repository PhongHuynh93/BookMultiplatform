package com.wind.book.data.mapping.wallup

import com.wind.book.data.mapping.Mapper
import com.wind.book.data.model.dto.wallup.RelatedResult
import com.wind.book.data.model.dto.wallup.UnsplashPhotoDto
import com.wind.book.model.wallup.PhotoDetail
import com.wind.book.model.wallup.RelatedCollectionResult
import com.wind.book.model.wallup.RelatedCollections
import com.wind.book.model.wallup.UnsplashPhoto
import com.wind.book.model.wallup.UserDetail

class UnsplashPhotoMapper : Mapper<UnsplashPhotoDto, UnsplashPhoto> {

    override fun apply(input: UnsplashPhotoDto): UnsplashPhoto {
        return input.run {
            UnsplashPhoto(
                id = id ?: "",
                createdAt = created_at ?: "",
                blurHash = blur_hash ?: "",
                color = color ?: "",
                thumbnail = urls?.thumb ?: "",
                smallImage = urls?.small ?: "",
                fullImage = urls?.full ?: "",
                sourceUrl = links?.html ?: "",
                views = views ?: 0,
                downloads = downloads ?: 0,
                likes = likes ?: 0,
                userDetail = UserDetail(
                    name = user?.name ?: "",
                    unsplashProfile = user?.links?.html ?: "",
                    image = user?.profile_image?.medium ?: "",
                    instaUserName = user?.instagram_username ?: "",
                    twitterUserName = user?.twitter_username ?: "",
                ),
                photoDetail = PhotoDetail(
                    width = width ?: 0,
                    height = height ?: 0,
                    _cameraName = exif?.name ?: "",
                    _aperture = exif?.aperture ?: "",
                    _focalLength = exif?.focal_length ?: "",
                    _exposureTime = exif?.exposure_time ?: "",
                    _iso = exif?.iso?.toString() ?: ""
                ),
                relatedCollections = RelatedCollections(
                    collections = related_collections?.results?.map { it.toUIModel() } ?: emptyList(),
                    total = related_collections?.total ?: 0
                )
            )
        }
    }
}

private fun RelatedResult.toUIModel() = RelatedCollectionResult(
    id = id ?: "",
    title = title ?: "",
    color = cover_photo?.color ?: "",
    coverPhoto = cover_photo?.urls?.regular ?: ""
)
