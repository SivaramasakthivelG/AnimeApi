package com.example.animeapi.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.animeapi.R
import java.util.Date

class VideoEndService : Service() {

    private val CHANNEL_ID = "video_end_channel"


    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val videoId = intent?.getStringExtra("video_id") ?: "Unknown"

        val endTime = Date()

        showNotification("Thanks for watching", "Video $videoId ended at $endTime")

        stopSelf()
        return START_NOT_STICKY

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, text: String) {
        val notification = Notification.Builder(this,CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1,notification)

    }

    private fun createNotificationChannel() {
        //create a channel
        //get system service
        //create noti channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Video End Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(p0: Intent?) = null



}