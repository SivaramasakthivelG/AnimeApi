package com.example.animeapi.ui.view_model

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapi.data.model.AnimeListModel
import com.example.animeapi.data.repository.AnimeRepo
import com.example.animeapi.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnimeListViewModel @Inject constructor(private val animeRepository: AnimeRepo): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AnimeListModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<AnimeListModel>>> = _uiState



    init {
        getAnime()
    }

    fun getAnime() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = animeRepository.getAnime()

                val animeList = response?.data?.map {
                    AnimeListModel(
                        id = it.mal_id,
                        title = it.title,
                        imageUrl = it.images.jpg.image_url,
                        score = it.score ?: 0.0,
                        episodes = it.episodes ?: 0
                    )
                } ?: emptyList()

                _uiState.value = UiState.Success(animeList)

            }catch (e: Exception){
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class UiState<out T> {
    object  Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error(val message: String): UiState<Nothing>()
}