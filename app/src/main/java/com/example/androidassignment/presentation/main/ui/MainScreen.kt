package com.example.androidassignment.presentation.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.androidassignment.domain.model.Item
import com.example.androidassignment.presentation.main.MainScreenIntent
import com.example.androidassignment.presentation.main.MainScreenState

@Composable
fun MainScreen(
    state: MainScreenState,
    onIntent: (MainScreenIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            // Loading State
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            // Error State
            state.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.errorMessage)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { onIntent(MainScreenIntent.FetchContent) }) { Text("Retry") }
                    }
                }
            }
            // Success State
            state.contentItems != null -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(state.contentItems) { item ->
                        ItemHierarchy(item, onImageClick = { intent -> onIntent(intent) })
                    }
                }
            }
        }
    }
}

@Composable
fun ItemHierarchy(
    item: Item,
    level: Int = 0,
    onImageClick: (MainScreenIntent) -> Unit
) {
    val fontSizes = listOf(24.sp, 20.sp, 18.sp, 16.sp)
    val fontSize = fontSizes.getOrElse(level) { 14.sp }
    val weight = if (level == 0) FontWeight.Bold else FontWeight.Normal

    when (item) {
        is Item.Page -> {
            Text(
                item.title,
                fontSize = fontSize,
                fontWeight = weight,
                modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
            )
            item.items.forEach {
                ItemHierarchy(it, level + 1, onImageClick)
            }
        }

        is Item.Section -> {
            Text(
                item.title,
                fontSize = fontSize,
                fontWeight = weight,
                modifier = Modifier.padding(start = (8 * level).dp, top = 6.dp, bottom = 4.dp)
            )
            item.items.forEach {
                ItemHierarchy(it, level + 1, onImageClick)
            }
        }

        is Item.QuestionText -> {
            Text(
                item.title,
                fontSize = fontSize,
                modifier = Modifier.padding(start = (8 * level).dp, top = 2.dp, bottom = 2.dp)
            )
        }

        is Item.QuestionImage -> {
            Column(modifier = Modifier.padding(start = (8 * level).dp, top = 4.dp, bottom = 4.dp)) {
                Text(item.title, fontSize = fontSize)
                Spacer(Modifier.height(2.dp))
                Image(
                    painter = rememberAsyncImagePainter(item.imageUrl),
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(72.dp)
                        .clickable { onImageClick(MainScreenIntent.ImageClicked(item.imageUrl, item.title)) }
                )
            }
        }
    }
}