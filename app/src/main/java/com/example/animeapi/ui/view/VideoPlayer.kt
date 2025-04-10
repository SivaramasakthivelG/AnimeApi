package com.example.animeapi.ui.view

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage

@Composable
fun YouTubeTrailerPlayer(youtubeUrl: String) {
    var showPlayer by remember { mutableStateOf(false) }

    val videoId = getYouTubeVideoId(youtubeUrl)
    val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

    if (showPlayer) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    setLayerType(WebView.LAYER_TYPE_HARDWARE, null)
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.mediaPlaybackRequiresUserGesture = false
                    webViewClient = WebViewClient()

                    loadUrl("https://www.youtube.com/embed/$videoId?autoplay=1&modestbranding=1&controls=1")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    } else {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = "thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { showPlayer = true }
        )
    }
}

fun getYouTubeVideoId(url: String): String {
    val regex = Regex("(?:v=|youtu\\.be/)([a-zA-Z0-9_-]{11})")
    return regex.find(url)?.groupValues?.get(1) ?: ""
}
