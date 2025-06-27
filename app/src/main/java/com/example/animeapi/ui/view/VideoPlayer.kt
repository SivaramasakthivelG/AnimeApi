package com.example.animeapi.ui.view

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.animeapi.data.service.VideoLoggerService
import com.example.animeapi.ui.view_model.VideoPlayerViewModel
import com.example.animeapi.utils.NotificationUtils
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YouTubePlayerView(
    videoId: String,
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentTime by viewModel.currentTime

    AndroidView(
        factory = { ctx ->
            val youTubePlayerView = YouTubePlayerView(ctx)
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, currentTime)

                    //this pauses the video at 30 secs
                    /*android.os.Handler(Looper.getMainLooper()).postDelayed({
                        youTubePlayer.pause()
                    },30000)
                     */

                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    viewModel.updateTime(second)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    if(state == PlayerConstants.PlayerState.ENDED){
                        //start a video
                        NotificationUtils.showVideoEndNotification(context, videoId)
                        val intent = Intent(context, VideoLoggerService::class.java).apply {
                            putExtra("VIDEO_ID", videoId)
                        }
                        ContextCompat.startForegroundService(context, intent)
                    }
                }

            })
            youTubePlayerView
        },
        modifier = Modifier
            .fillMaxSize()
    )
}

