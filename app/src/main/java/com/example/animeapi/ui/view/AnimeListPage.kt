package com.example.animeapi.ui.view

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animeapi.XmlView
import com.example.animeapi.R
import com.example.animeapi.data.model.AnimeListModel
import com.example.animeapi.data.service.NetworkConnectivityObserver
import com.example.animeapi.ui.view_model.AnimeListViewModel
import com.example.animeapi.ui.view_model.UiState
import com.example.animeapi.utils.CommonUtils
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListPage(
    modifier: Modifier = Modifier,
    viewModel: AnimeListViewModel,
    innerPadding: PaddingValues,
    onAnimeClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val connectivityObserver = remember { NetworkConnectivityObserver(context) }
    val isConnected by connectivityObserver.connectivityFlow.collectAsState(initial = true)

    LaunchedEffect(isConnected) {
        viewModel.getAnime()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Top Anime Shows") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            XmlView { a, b ->
                Toast.makeText(context, "a,$b", Toast.LENGTH_SHORT).show()
            }
        }
    ) { paddingValues ->

        val combinedPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 6.dp,
            bottom = innerPadding.calculateBottomPadding() + 6.dp
        )

        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = {
                if(isConnected){
                    viewModel.getAnime()

                }else Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show()
            },
            modifier = modifier.fillMaxSize()
        ) {

            when (uiState) {
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

                    if (animeList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(combinedPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No anime found. \nPlease check Internet connection", style = MaterialTheme.typography.bodyMedium)
                        }
                    } else {
                        LazyColumn(contentPadding = combinedPadding) {
                            items(animeList) { anime ->
                                AnimeListItem(anime = anime) {
                                    onAnimeClick(anime.id)
                                }
                            }
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
    }
}


@Composable
fun AnimeListItem(anime: AnimeListModel, onclick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onclick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = anime.imageUrl.ifEmpty { R.drawable.ic_launcher_foreground },
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

