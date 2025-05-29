package com.example.animeapi.ui.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class VideoPlayerViewModel: ViewModel() {

    private val _videoEndedEvent = MutableStateFlow<String>("")
    val videoEndedEvent = _videoEndedEvent;

    fun onVideoEnded(videoId: String) {
        _videoEndedEvent.value = videoId
    }



}