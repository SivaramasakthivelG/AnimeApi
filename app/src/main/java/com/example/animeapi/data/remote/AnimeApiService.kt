package com.example.animeapi.data.model

import com.example.animeapi.data.modeldata.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApiService {

    @GET("top/anime")
    suspend fun getTopAnime(): Response<AnimeResponse>

    @GET("anime/{mal_id}")
    suspend fun getAnimeDetail(@Path("mal_id") animeId: Int): Response<AnimeDetailResponse>

}