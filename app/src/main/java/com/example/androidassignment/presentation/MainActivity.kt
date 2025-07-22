package com.example.androidassignment.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidassignment.presentation.main.MainScreenIntent
import com.example.androidassignment.presentation.main.MainViewModel
import com.example.androidassignment.presentation.main.navigator.MainNavigator
import com.example.androidassignment.presentation.main.ui.ImageScreen
import com.example.androidassignment.presentation.main.ui.MainScreen
import com.example.androidassignment.presentation.ui.theme.AndroidAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidAssignmentTheme {
                MainApp(mainNavigator)
            }
        }
    }
}

@Composable
fun MainApp(mainNavigator: MainNavigator) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()

    val state by mainViewModel.uiState.collectAsState()

    DisposableEffect(navController) {
        mainNavigator.setNavController(navController)
        onDispose {
            mainNavigator.setNavController(null)
        }
    }

    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                state = state,
                onIntent = { mainViewModel.sendIntent(it) },
            )
        }
        composable(
            route = "imageFull?src={src}&title={title}",
            arguments = listOf(
                navArgument("src") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val src = backStackEntry.arguments?.getString("src") ?: ""
            val title = backStackEntry.arguments?.getString("title")
            ImageScreen(src = src, title = title)
        }
    }
}
