package com.example.androidassignment.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class ItemDto {
    abstract val type: String
    abstract val title: String?
}

    @JsonClass(generateAdapter = true)
    data class PageDto(
        @Json(name = "type") override val type: String = "page",
        @Json(name = "title") override val title: String?,
        @Json(name = "items") val items: List<ItemDto>? = null
    ) : ItemDto()

    @JsonClass(generateAdapter = true)
    data class SectionDto(
        @Json(name = "type") override val type: String = "section",
        @Json(name = "title") override val title: String?,
        @Json(name = "items") val items: List<ItemDto>? = null
    ) : ItemDto()

    @JsonClass(generateAdapter = true)
    data class QuestionTextDto(
        @Json(name = "type") override val type: String = "text",
        @Json(name = "title") override val title: String?
    ) : ItemDto()

    @JsonClass(generateAdapter = true)
    data class QuestionImageDto(
        @Json(name = "type") override val type: String = "image",
        @Json(name = "title") override val title: String?,
        @Json(name = "src") val src: String
    ) : ItemDto()

