package com.example.animeapi.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapi.data.model.AnimeDetailModel
import com.example.animeapi.data.repository.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(private val animeRepository: AnimeRepo): ViewModel() {

    private val _uiStateDetail = MutableStateFlow<UiStateDetail<AnimeDetailModel>>(UiStateDetail.Loading)
    val uiStateDetail: StateFlow<UiStateDetail<AnimeDetailModel>> = _uiStateDetail

    fun getAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            try {
                _uiStateDetail.value = UiStateDetail.Loading
                val response = animeRepository.getAnimeDetails(animeId)

                val anime = response?.let{
                    AnimeDetailModel(
                        title = it.title,
                        synopsis = it.synopsis,
                        episodes = it.episodes,
                        rating = it.rating,
                        genres = it.genres,
                        trailer = it.trailer,
                        images = it.images,
                    )
                }

                if (anime != null) {
                    _uiStateDetail.value = UiStateDetail.Success(anime)
                } else {
                    _uiStateDetail.value = UiStateDetail.Error("null data")
                }

            }catch (e: Exception){
                _uiStateDetail.value = UiStateDetail.Error(e.message ?: "error not found")
            }
        }
    }

}

sealed class UiStateDetail<out T> {
    object  Loading: UiStateDetail<Nothing>()
    data class Success<T>(val data: T): UiStateDetail<T>()
    data class Error(val message: String): UiStateDetail<Nothing>()
}