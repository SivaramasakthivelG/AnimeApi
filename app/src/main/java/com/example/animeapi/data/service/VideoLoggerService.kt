package com.example.animeapi.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.animeapi.R


class VideoLoggerService: Service() {

    private val CHANNEL_ID = "video_log_channel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val videoId = intent?.getStringExtra("VIDEO_ID") ?: "UNKNOWN"


        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Logging video event")
            .setContentText("Video $videoId ended")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        Log.d("VideoLoggerService", "Video ended: $videoId")

        startForeground(101, notification)

        Log.d("VideoLoggerService", "Video ended: $videoId")

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Video Logger Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        Log.d("VideoLoggerService", "Service destroyed")
        super.onDestroy()
    }
}