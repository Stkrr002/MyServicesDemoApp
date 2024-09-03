package com.alpharays.myservicesdemoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class MyServicesDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "my_foreground_service",
            "Running notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}