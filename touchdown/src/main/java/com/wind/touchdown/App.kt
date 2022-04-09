package com.wind.touchdown

import android.app.Application
import com.wind.book.AppInfo
import com.wind.book.initKoin
import com.wind.book.json
import com.wind.book.model.touchdown.Brand
import com.wind.book.model.touchdown.Player
import com.wind.book.model.touchdown.Product
import com.wind.book.model.touchdown.TouchDownCategory
import com.wind.book.util.AppContext
import com.wind.touchdown.data.AssetUtil
import kotlinx.serialization.decodeFromString
import org.koin.dsl.module

lateinit var players: List<Player>
lateinit var brands: List<Brand>
lateinit var touchDownCategories: List<TouchDownCategory>
lateinit var products: List<Product>

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<AppContext> { this@App }
                single<AppInfo> { AndroidAppInfo }
            },
        )
        players = json.decodeFromString(AssetUtil.loadJSONFromAsset(this, "player.json"))
        brands = json.decodeFromString(AssetUtil.loadJSONFromAsset(this, "brand.json"))
        touchDownCategories = json.decodeFromString(AssetUtil.loadJSONFromAsset(this, "category.json"))
        products = json.decodeFromString(AssetUtil.loadJSONFromAsset(this, "product.json"))
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
