package org.test.common_imageloading

import android.app.Application
import android.content.Context
import app.tivi.appinitializers.AppInitializer
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.util.CoilUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Inject

@OptIn(ExperimentalCoilApi::class)
class CoilAppInitializer @Inject constructor(
    private val tmdbImageEntityInterceptor: TmdbImageEntityCoilInterceptor,
    private val episodeEntityInterceptor: EpisodeEntityCoilInterceptor,
    private val okHttpClient: OkHttpClient,
    @ApplicationContext private val context: Context
) : AppInitializer {
    override fun init(application: Application) {
        val coilOkHttpClient = okHttpClient.newBuilder()
            .cache(CoilUtils.createDefaultCache(context))
            .build()
        Coil.setImageLoader {
            ImageLoader.Builder(application)
                .componentRegistry {
                    add(tmdbImageEntityInterceptor)
                    add(episodeEntityInterceptor)
                }
                .okHttpClient(coilOkHttpClient)
                .build()
        }
    }
}
