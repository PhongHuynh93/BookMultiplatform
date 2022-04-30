package org.test.common_imageloading

import app.tivi.util.PowerController
import app.tivi.util.SaveData
import coil.annotation.ExperimentalCoilApi
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.size.PixelSize
import coil.size.Size
import com.wind.book.model.tivi.ImageType
import com.wind.book.model.tivi.TmdbImageEntity
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.test.tmdb.TmdbImageUrlProvider
import java.security.Provider

@ExperimentalCoilApi
class TmdbImageEntityCoilInterceptor @Inject constructor(
    private val tmdbImageUrlProvider: Provider<TmdbImageUrlProvider>,
    private val powerController: PowerController
) : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = when (val data = chain.request.data) {
            is TmdbImageEntity -> {
                chain.request.newBuilder()
                    .data(map(data, chain.size))
                    .build()
            }
            else -> chain.request
        }
        return chain.proceed(request)
    }

    private fun map(data: TmdbImageEntity, size: Size): HttpUrl {
        val width = if (size is PixelSize) {
            when (powerController.shouldSaveData()) {
                is SaveData.Disabled -> size.width
                // If we can't download hi-res images, we load half-width images (so ~1/4 in size)
                is SaveData.Enabled -> size.width / 2
            }
        } else 0

        val urlProvider = tmdbImageUrlProvider.get()
        return when (data.type) {
            ImageType.BACKDROP -> urlProvider.getBackdropUrl(data.path, width).toHttpUrl()
            ImageType.POSTER -> urlProvider.getPosterUrl(data.path, width).toHttpUrl()
            ImageType.LOGO -> urlProvider.getLogoUrl(data.path, width).toHttpUrl()
        }
    }
}
