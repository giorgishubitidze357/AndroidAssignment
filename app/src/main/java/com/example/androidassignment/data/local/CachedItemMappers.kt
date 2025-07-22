package com.example.androidassignment.data.local

import com.example.androidassignment.domain.model.Item
import java.util.UUID

fun Item.flattenWithParent(parentEntityId: String? = null): List<CachedItemEntity> {
    val thisId = UUID.randomUUID().toString()
    return when (this) {
        is Item.Page -> {
            val node = CachedItemEntity(
                type = "page",
                title = title,
                entityId = thisId,
                parentEntityId = parentEntityId
            )
            listOf(node) + items.flatMap { it.flattenWithParent(thisId) }
        }
        is Item.Section -> {
            val node = CachedItemEntity(
                type = "section",
                title = title,
                entityId = thisId,
                parentEntityId = parentEntityId
            )
            listOf(node) + items.flatMap { it.flattenWithParent(thisId) }
        }
        is Item.QuestionText -> {
            listOf(
                CachedItemEntity(
                    type = "text",
                    title = title,
                    entityId = thisId,
                    parentEntityId = parentEntityId
                )
            )
        }

        is Item.QuestionImage -> {
            listOf(
                CachedItemEntity(
                    type = "image",
                    title = title,
                    imageUrl = imageUrl,
                    entityId = thisId,
                    parentEntityId = parentEntityId
                )
            )
        }
    }
}

fun buildItemTreeByEntityId(
    entities: List<CachedItemEntity>,
    parentEntityId: String? = null
): List<Item> {
    return entities
        .filter { it.parentEntityId == parentEntityId }
        .map { entity ->
            when (entity.type) {
                "page" -> Item.Page(
                    title = entity.title ?: "Untitled Page",
                    items = buildItemTreeByEntityId(entities, entity.entityId)
                )
                "section" -> Item.Section(
                    title = entity.title ?: "Untitled Section",
                    items = buildItemTreeByEntityId(entities, entity.entityId)
                )
                "text" -> Item.QuestionText(
                    title = entity.title ?: ""
                )

                "image" -> Item.QuestionImage(
                    title = entity.title ?: "",
                    imageUrl = entity.imageUrl ?: ""
                )

                else -> throw IllegalStateException("Unknown cached type ${entity.type}")
            }
        }
}
