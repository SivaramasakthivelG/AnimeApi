package com.example.animeapi.data.model

import com.google.gson.annotations.SerializedName


data class AnimeDetailModel(
    val title: String,
    val synopsis: String,
    val episodes: Int,
    val rating: String,
    val trailer: Trailer?,
    val images: Images?,
    val genres: List<Genre>
)

data class Trailer(
    @SerializedName("url")
    val url: String?
)

data class Images(
    val jpg: JpgImage
)

data class JpgImage(
    @SerializedName("image_url")
    val imageUrl: String
)

data class Genre(
    val name: String
)