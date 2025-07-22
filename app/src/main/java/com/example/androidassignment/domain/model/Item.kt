package com.example.androidassignment.domain.model

sealed class Item {
    abstract val title: String?

    data class Page(
        override val title : String,
        val items: List<Item>
    ) : Item()

    data class Section(
        override val title : String,
        val items: List<Item>
    ) : Item()

    data class QuestionText(
        override val title : String,
    ) : Item()

    data class QuestionImage(
        override val title : String,
        val imageUrl: String
    ) : Item()
}