package com.example.androidassignment.presentation.main.navigator

import androidx.navigation.NavController
import java.net.URLEncoder
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor() : MainNavigator{

    private var navController: NavController? = null

    override fun setNavController(navController: NavController?) {
        this.navController = navController
    }

    override fun navigateToImageDetail(imageUrl: String, imageTitle: String?) {
        navController?.let {
            val encodedSrc = URLEncoder.encode(imageUrl, "UTF-8")
            val encodedTitle = imageTitle?.let { URLEncoder.encode(it, "UTF-8") }
            it.navigate("imageFull?src=$encodedSrc&title=$encodedTitle")
        } ?: run {
            println("Error: NavController not set in MainNavigator when trying to navigate to image detail.")
        }
    }

    override fun navigateBack() {
        navController?.navigateUp() ?: run {
            println("Error: NavController not set when trying to navigate back.")
        }
    }

}