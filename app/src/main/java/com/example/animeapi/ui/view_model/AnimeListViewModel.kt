package com.example.animeapi.ui.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapi.data.model.AnimeResponse
import com.example.animeapi.data.repository.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnimeListViewModel @Inject constructor(private val animeRepository: AnimeRepo): ViewModel() {

    var animeList by mutableStateOf<List<AnimeResponse>>(emptyList())
        private set

    init {
        getAnime()
    }

    private fun getAnime() {
        viewModelScope.launch {
            animeList = (animeRepository.getAnime()?.toList() ?: emptyList()) as List<AnimeResponse>
        }
    }
}