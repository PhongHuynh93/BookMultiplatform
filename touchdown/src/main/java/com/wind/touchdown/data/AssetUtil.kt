package com.wind.touchdown.data

import android.content.Context
import java.io.InputStream

object AssetUtil {

    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        val `is`: InputStream = context.assets.open(jsonFileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer)
    }
}
