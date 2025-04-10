package com.example.animeapi.ui.navigation

sealed class Screens(val route: String) {

    object AnimeList: Screens("anime_list")
    object AnimeDetail: Screens("anime_detail/{animeId}"){
        fun createRoute(animeId: Int) = "anime_detail/$animeId"
    }


}