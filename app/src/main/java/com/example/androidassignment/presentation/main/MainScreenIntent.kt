package com.example.androidassignment.presentation.main

sealed class MainScreenIntent {
    object FetchContent : MainScreenIntent()
    data class ImageClicked(val imageUrl: String, val imageTitle: String) : MainScreenIntent()
    object ErrorMessageShown : MainScreenIntent()
}