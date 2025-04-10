package com.example.animeapi.data.repository

import com.example.animeapi.data.model.AnimeApiService
import com.example.animeapi.data.model.AnimeDetailModel
import com.example.animeapi.data.modeldata.AnimeResponse
import javax.inject.Inject

class AnimeRepo @Inject constructor(
    private val service: AnimeApiService
) {

    suspend fun getAnime(): AnimeResponse? {
        return try {
            val response = service.getTopAnime();
            println(response.body())
            if (response.isSuccessful)
                response.body()
            else null
        }catch (e: Exception){
            null
        }
    }

    suspend fun getAnimeDetails(animeId: Int): AnimeDetailModel? {
        return try {
            val response = service.getAnimeDetail(animeId);
            println(response.body())
            if (response.isSuccessful)
                response.body()?.data
            else null
        }catch (e: Exception){
            null
        }
    }




}