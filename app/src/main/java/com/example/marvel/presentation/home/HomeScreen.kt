package com.example.marvel.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.fresco.FrescoImage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState(initial = HomeUiState.Loading)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(12.dp).fillMaxWidth()
    ) {
        when (uiState) {
            HomeUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            is HomeUiState.ComicLoaded -> {
                val comic = (uiState as HomeUiState.ComicLoaded).comic
                FrescoImage(
                    imageUrl = comic.artworkUrl.toExternalForm(),
                    modifier = Modifier
                        .width(300.dp)
                        .height(400.dp)
                )
                TwelveSpacer()
                Text(
                    text = "Issue: ${comic.issueNumber}",
                    color = Color.White,
                    modifier = Modifier.padding(end = 12.dp)
                )
                TwelveSpacer()
                Text(text = comic.title, color = Color.White)
                TwelveSpacer()
                Text(text = comic.description, color = Color.White)
                TwelveSpacer()
                Text(text = comic.copyright, color = Color.Gray)
                TwelveSpacer()
                Text(text = comic.attribution, color = Color.Gray)
            }

            is HomeUiState.Error -> {
                Text(text = (uiState as HomeUiState.Error).message)
            }
        }
    }
}

@Composable
fun TwelveSpacer() {
    Spacer(modifier = Modifier.height(12.dp))
}
