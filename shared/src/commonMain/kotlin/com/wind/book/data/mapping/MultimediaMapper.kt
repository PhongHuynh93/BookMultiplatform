package com.wind.book.data.mapping

import com.wind.book.data.model.dto.MultimediaDto
import com.wind.book.model.Multimedia

class MultimediaMapper : Mapper<MultimediaDto, Multimedia?> {
    override fun apply(input: MultimediaDto): Multimedia? {
        return input.run {
            Multimedia(
                caption = caption.orEmpty(),
                type = type.orEmpty(),
                url = url.orEmpty()
            )
        }
    }
}

class MultiMediasMapper : Mapper<List<MultimediaDto?>?, List<Multimedia>> {
    override fun apply(input: List<MultimediaDto?>?): List<Multimedia> {
        val list = ArrayList<Multimedia>()
        input?.forEach {
            if (it != null) {
                list.add(
                    Multimedia(
                        caption = it.caption.orEmpty(),
                        type = it.type.orEmpty(),
                        url = it.url.orEmpty()
                    )
                )
            }
        }
        return list
    }
}
