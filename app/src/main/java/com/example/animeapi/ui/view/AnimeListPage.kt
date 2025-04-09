package com.example.animeapi.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.animeapi.ui.view_model.AnimeListViewModel

@Composable
fun AnimeListPage(
    modifier: Modifier.Companion,
    viewModel: AnimeListViewModel,
    innerPadding: PaddingValues
) {
    val animeList = viewModel.animeList[0]

    LazyColumn {
        items(animeList){anime->
            Text(text = anime.title)
        }
    }

}