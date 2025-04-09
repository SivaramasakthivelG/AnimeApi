package com.example.animeapi.data.repository

import com.example.animeapi.data.model.AnimeApiService
import com.example.animeapi.data.model.AnimeResponse
import javax.inject.Inject

class AnimeRepo @Inject constructor(
    private val service: AnimeApiService
) {

    suspend fun getAnime(): AnimeResponse? {
        return try {
            val response = service.getTopAnime();
            if (response.isSuccessful)
                response.body()
            else null
        }catch (e: Exception){
            null
        }
    }


}