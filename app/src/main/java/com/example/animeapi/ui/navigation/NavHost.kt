package com.example.animeapi.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.animeapi.ui.view.AnimeDetailPage
import com.example.animeapi.ui.view.AnimeListPage
import com.example.animeapi.ui.view_model.AnimeDetailsViewModel
import com.example.animeapi.ui.view_model.AnimeListViewModel

@Composable
fun NavHostAnime(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(navController = navController, startDestination = "anime_list") {

        composable(Screens.AnimeList.route){
            val listViewModel: AnimeListViewModel = hiltViewModel()
            AnimeListPage(viewModel = listViewModel, innerPadding = innerPadding){animeId ->
                navController.navigate(Screens.AnimeDetail.createRoute(animeId))
            }
        }
        composable(Screens.AnimeDetail.route){backStackEntry ->
            val detailViewModel: AnimeDetailsViewModel = hiltViewModel()

            val animeId = backStackEntry.arguments?.getString("animeId")
            if(animeId != null){
                AnimeDetailPage(viewModel = detailViewModel, id = animeId.toInt())
            }

        }

    }
}