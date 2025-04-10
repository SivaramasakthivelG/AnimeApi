package com.example.animeapi.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animeapi.data.model.AnimeDetailModel
import com.example.animeapi.ui.view_model.AnimeDetailsViewModel
import com.example.animeapi.ui.view_model.UiStateDetail

@Composable
fun AnimeDetailPage(viewModel: AnimeDetailsViewModel, id: Int) {
    val uiState by viewModel.uiStateDetail.collectAsState()

    LaunchedEffect(id) {
        viewModel.getAnimeDetails(id)
    }

    when (uiState) {
        is UiStateDetail.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiStateDetail.Error -> {
            val message = (uiState as UiStateDetail.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $message", color = MaterialTheme.colorScheme.error)
            }
        }

        is UiStateDetail.Success -> {
            val anime = (uiState as UiStateDetail.Success<AnimeDetailModel>).data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (anime.trailer != null && anime.trailer.url != null) {
                    YouTubePlayerView(getYouTubeVideoId(anime.trailer.url))
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    AsyncImage(
                        model = anime.images?.jpg?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(anime.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Episodes: ${anime.episodes ?: "unknown"}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))

                Text("Rating: ${anime.rating ?: "no rating"}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "Genres: ${anime.genres.joinToString { it.name }}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    anime.synopsis ?: "null",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

fun getYouTubeVideoId(url: String): String {
    val regex = Regex("(?:v=|vi=|youtu\\.be/|embed/)([a-zA-Z0-9_-]{11})")
    return regex.find(url)?.groupValues?.get(1) ?: ""
}
