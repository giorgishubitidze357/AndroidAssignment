package com.example.androidassignment.data.remote.dto

import com.example.androidassignment.domain.model.Item

fun ItemDto.toDomain() : Item = when(this){
    is PageDto -> Item.Page(title ?: "Untitled Page", items?.map {it.toDomain()} ?: emptyList())
    is SectionDto -> Item.Section(title ?: "Untitled Section", items?.map {it.toDomain()} ?: emptyList())
    is QuestionTextDto -> Item.QuestionText(title ?: "No Question Text")
    is QuestionImageDto -> Item.QuestionImage(title ?: "Untitled Image", src)
}