package com.wind.book.viewmodel.model.wallup

import com.benasher44.uuid.uuid4
import com.wind.book.model.Identifiable
import com.wind.book.model.wallup.ColorItem
import com.wind.book.model.wallup.UnsplashPhoto
import com.wind.book.model.wallup.WallupCategory

sealed class Home : Identifiable {

    data class ColorList(val colorList: List<ColorItem>) : Home() {
        override val id: String
            get() = uuid4().toString()
    }

    data class CategoryList(val wallupCategoryList: List<WallupCategory>) : Home() {
        override val id: String
            get() = uuid4().toString()
    }

    data class RandomPhotoList(val randomPhotoList: List<UnsplashPhoto>) : Home() {
        override val id: String
            get() = uuid4().toString()
    }
}
