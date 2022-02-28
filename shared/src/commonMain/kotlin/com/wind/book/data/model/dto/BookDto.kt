package com.wind.book.data.model.dto

import com.wind.book.data.util.BookSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Book response dto
@Serializable
data class BookResDto(
    @SerialName("copyright")
    val copyright: String? = null,
    @SerialName("last_modified")
    val lastModified: String? = null,
    @SerialName("num_results")
    val numResults: Int? = null,
    @Serializable(BookSerializer::class)
    @SerialName("results")
    val results: BookListDto? = null,
    @SerialName("status")
    val status: String? = null
)

@Serializable
data class BookListDto(
    @SerialName("bestsellers_date")
    val bestsellersDate: String? = null,
    @SerialName("books")
    val books: List<BookDto?>? = null,
    // @SerialName("corrections")
    // val corrections: List<Any?>? = null,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("list_name")
    val listName: String? = null,
    @SerialName("list_name_encoded")
    val listNameEncoded: String? = null,
    @SerialName("next_published_date")
    val nextPublishedDate: String? = null,
    @SerialName("normal_list_ends_at")
    val normalListEndsAt: Int? = null,
    @SerialName("previous_published_date")
    val previousPublishedDate: String? = null,
    @SerialName("published_date")
    val publishedDate: String? = null,
    @SerialName("published_date_description")
    val publishedDateDescription: String? = null,
    @SerialName("updated")
    val updated: String? = null
)

@Serializable
data class BookDto(
    @SerialName("age_group")
    val ageGroup: String? = null,
    @SerialName("amazon_product_url")
    val amazonProductUrl: String? = null,
    @SerialName("article_chapter_link")
    val articleChapterLink: String? = null,
    @SerialName("asterisk")
    val asterisk: Int? = null,
    @SerialName("author")
    val author: String? = null,
    @SerialName("book_image")
    val bookImage: String? = null,
    @SerialName("book_image_height")
    val bookImageHeight: Int? = null,
    @SerialName("book_image_width")
    val bookImageWidth: Int? = null,
    @SerialName("book_review_link")
    val bookReviewLink: String? = null,
    @SerialName("book_uri")
    val bookUri: String? = null,
    @SerialName("buy_links")
    val buyLinks: List<BuyLink?>? = null,
    @SerialName("contributor")
    val contributor: String? = null,
    @SerialName("contributor_note")
    val contributorNote: String? = null,
    @SerialName("dagger")
    val dagger: Int? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("first_chapter_link")
    val firstChapterLink: String? = null,
    @SerialName("isbns")
    val isbns: List<Isbn?>? = null,
    @SerialName("price")
    val price: String? = null,
    @SerialName("primary_isbn10")
    val primaryIsbn10: String? = null,
    @SerialName("primary_isbn13")
    val primaryIsbn13: String? = null,
    @SerialName("publisher")
    val publisher: String? = null,
    @SerialName("rank")
    val rank: Int? = null,
    @SerialName("rank_last_week")
    val rankLastWeek: Int? = null,
    @SerialName("sunday_review_link")
    val sundayReviewLink: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("weeks_on_list")
    val weeksOnList: Int? = null
) {
    @Serializable
    data class BuyLink(
        @SerialName("name")
        val name: String? = null,
        @SerialName("url")
        val url: String? = null
    )

    @Serializable
    data class Isbn(
        @SerialName("isbn10")
        val isbn10: String? = null,
        @SerialName("isbn13")
        val isbn13: String? = null
    )
}
