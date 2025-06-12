package com.example.animeapi.ui.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class VideoPlayerViewModel: ViewModel() {

    private val _currentTime = mutableStateOf(0f)
    val currentTime: State<Float> = _currentTime

    fun updateTime(seconds: Float) {
        _currentTime.value = seconds
    }



}