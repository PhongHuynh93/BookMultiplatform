package com.wind.book.model.tivi

enum class ImageType(val storageKey: String) {
    BACKDROP("backdrop"),
    POSTER("poster"),
    LOGO("logo"),
}

interface TiviEntity {
    val id: Long
}

interface TmdbImageEntity : TiviEntity {
    val path: String
    val type: ImageType
    val language: String?
    val rating: Float
    val isPrimary: Boolean
}
