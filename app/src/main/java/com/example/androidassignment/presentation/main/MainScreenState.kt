package com.example.androidassignment.presentation.main

import com.example.androidassignment.domain.model.Item

data class MainScreenState(
    val isLoading: Boolean = false,
    val contentItems: List<Item>? = null,
    val errorMessage: String? = null,
    val snackbarMessage: String? = null
)