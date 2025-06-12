package com.example.animeapi.utils

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.animeapi.R

object NotificationUtils {

    private const val CHANNEL_ID = "Video_end_channel"

    @RequiresApi(Build.VERSION_CODES.O)
    fun showVideoEndNotification(context: Context, videoId: String){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Video End Notification",
            android.app.NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = Notification.Builder(context, CHANNEL_ID)
            .setContentTitle("Thanks for watching")
            .setContentText("Video $videoId has ended")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager.notify(1, notification)

    }


}