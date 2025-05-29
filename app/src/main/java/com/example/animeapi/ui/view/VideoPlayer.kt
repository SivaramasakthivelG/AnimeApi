package com.example.animeapi.ui.view

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.animeapi.data.service.VideoEndService
import com.example.animeapi.ui.view_model.VideoPlayerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerView(videoId: String,viewModel: VideoPlayerViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val videoEnded by viewModel.videoEndedEvent.collectAsState()

    LaunchedEffect(videoEnded) {
        videoEnded?.let {
            val intent = Intent(context, VideoEndService::class.java).apply {
                putExtra("VIDEO_ID", it)
            }
            context.startService(intent)
        }
    }


    AndroidView(
        factory = { ctx ->
            val youTubePlayerView = YouTubePlayerView(ctx)
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)

                    //this pauses the video at 30 secs
                    /*android.os.Handler(Looper.getMainLooper()).postDelayed({
                        youTubePlayer.pause()
                    },30000)
                     */



                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    if(state == PlayerConstants.PlayerState.ENDED){
                        //start a video
                        viewModel.onVideoEnded(videoId)
                    }
                }
            })
            youTubePlayerView
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

