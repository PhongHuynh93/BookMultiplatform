package com.wind.book.model.touchdown

import com.wind.book.Parcelable
import com.wind.book.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val image: String,
    val price: Int,
    val description: String,
    val color: List<Double>
) : Parcelable {

    fun formattedPrice() = "$$price"
}
