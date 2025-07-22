package com.example.androidassignment.presentation.main.navigator

import androidx.navigation.NavController

interface MainNavigator {
    fun navigateToImageDetail(imageUrl: String, imageTitle: String?)
    fun navigateBack()
    fun setNavController(navController: NavController?)
}