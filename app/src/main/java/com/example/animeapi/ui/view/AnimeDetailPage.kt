package com.example.animeapi.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animeapi.data.model.AnimeDetailModel
import com.example.animeapi.ui.view_model.AnimeDetailsViewModel
import com.example.animeapi.ui.view_model.UiStateDetail

@Composable
fun AnimeDetailPage(viewModel: AnimeDetailsViewModel,id: Int) {

    val uiState by viewModel.uiStateDetail.collectAsState()

    LaunchedEffect(key1 = id) {
        viewModel.getAnimeDetails(id)
    }

    when (uiState) {
        is UiStateDetail.Loading -> CircularProgressIndicator()
        is UiStateDetail.Error -> Text("Error: ${(uiState as UiStateDetail.Error).message}")
        is UiStateDetail.Success -> {
            val anime = (uiState as UiStateDetail.Success<AnimeDetailModel>).data
            Column(modifier = Modifier.padding(16.dp)) {
                if (anime.trailer != null) {
                    YouTubeTrailerPlayer(youtubeUrl = anime.trailer.url!!)
                } else {
                    AsyncImage(
                        model = anime.images?.jpg?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Text(anime.title, style = MaterialTheme.typography.headlineMedium)
                Text("Episodes: ${anime.episodes}")
                Text("Rating: ${anime.rating}")
                Text("Genres: ${anime.genres.joinToString()}")
                Text(anime.synopsis)
            }
        }
    }

}