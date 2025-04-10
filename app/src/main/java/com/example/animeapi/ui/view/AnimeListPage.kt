package com.example.animeapi.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animeapi.data.model.AnimeListModel
import com.example.animeapi.ui.view_model.AnimeListViewModel
import com.example.animeapi.ui.view_model.UiState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListPage(
    modifier: Modifier = Modifier,
    viewModel: AnimeListViewModel,
    innerPadding: PaddingValues,
    onAnimeClick: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Top Anime shows") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = Color.White
                )
            )
        },
        content = {paddingValues ->
            val combinedPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 6.dp,
                bottom = innerPadding.calculateBottomPadding() + 6.dp
            )

            when(uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(combinedPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    val animeList = (uiState as UiState.Success<List<AnimeListModel>>).data
                    LazyColumn(
                        contentPadding = combinedPadding
                    ) {
                        items(animeList) { anime ->
                            AnimeListItem(anime, onclick = {
                                onAnimeClick(anime.id)
                            })
                        }
                    }
                }

                is UiState.Error -> {
                    val message = (uiState as UiState.Error).message
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(combinedPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Oops! $message",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun AnimeListItem(anime: AnimeListModel, onclick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{onclick()},
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(anime.title, style = MaterialTheme.typography.titleMedium)
                Text("Episodes: ${anime.episodes}", style = MaterialTheme.typography.bodySmall)
                Text("Rating: ${anime.score}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

