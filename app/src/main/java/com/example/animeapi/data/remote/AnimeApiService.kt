package com.example.animeapi.data.model

import retrofit2.Response
import retrofit2.http.GET

interface AnimeApiService {

    @GET("top/anime")
    suspend fun getTopAnime(): Response<AnimeResponse>

}