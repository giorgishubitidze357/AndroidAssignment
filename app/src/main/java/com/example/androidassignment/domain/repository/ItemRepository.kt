package com.example.androidassignment.domain.repository

import com.example.androidassignment.domain.model.Item

interface ItemRepository {
    suspend fun getHierarchy(): Result<List<Item>>
}