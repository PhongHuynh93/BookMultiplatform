package com.wind.book.data.util

import com.wind.book.data.model.dto.BookListDto
import com.wind.book.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

/**
 */
@OptIn(ExperimentalSerializationApi::class)
object BookSerializer : KSerializer<BookListDto?> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BookSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): BookListDto? {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val jsonElement = input.decodeJsonElement()
        return if (jsonElement is JsonObject) {
            // JSON object then parse to BookListDto
            json.decodeFromString(jsonElement.toString())
        } else {
            // JSON array then return null
            null
        }
    }

    override fun serialize(encoder: Encoder, value: BookListDto?) {
    }
}
