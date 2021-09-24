package com.wind.book.model

data class Article(
    val section: String,
    val subSection: String,
    val abstract: String,
    val title: String,
    val url: String,
    val multimedia: List<Multimedia>,
    val date: String,
)

data class Multimedia(
    val caption: String,
    val type: String,
    val url: String,
)
