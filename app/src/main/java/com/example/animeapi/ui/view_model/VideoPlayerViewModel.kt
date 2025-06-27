package com.example.animeapi.ui.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VideoPlayerViewModel: ViewModel() {

    private val _currentTime = mutableStateOf(0f)
    val currentTime: State<Float> = _currentTime

    fun updateTime(seconds: Float) {
        _currentTime.value = seconds
    }



}