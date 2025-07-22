package com.example.androidassignment.data.remote

import com.example.androidassignment.data.remote.dto.ItemDto
import retrofit2.http.GET

interface ItemApiService {
    @GET("f118b9f0-6f84-435e-85d5-faf4453eb72a")
    suspend fun fetchItem(): ItemDto
}
