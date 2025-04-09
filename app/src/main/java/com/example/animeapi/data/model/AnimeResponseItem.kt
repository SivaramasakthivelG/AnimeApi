package com.example.animeapi.data.model

import android.provider.MediaStore

data class AnimeResponseItem(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: MediaStore.Images
)
