package com.alpharays.myservicesdemoapp.foregroundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.alpharays.myservicesdemoapp.R

class MyForegroundService : Service() {

    private val CHANNEL_ID = "my_foreground_service"
    private val NOTIFICATION_ID = 1
    private var countdownTime = 60
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        when (action) {
            MyForegroundServiceActions.START.toString() -> start()
            MyForegroundServiceActions.STOP.toString() -> stopSelf()
        }

        return START_STICKY
    }

    private fun start() {
        // Create notification channel
        createNotificationChannel()

        // Start with the initial notification
        val notification = createNotification(countdownTime).build()
        startForeground(NOTIFICATION_ID, notification)

        // Start the countdown
        handler = Handler(Looper.getMainLooper())
        startTimer()
    }

    private fun startTimer() {
        runnable = object : Runnable {
            override fun run() {
                if (countdownTime >= 0) {
                    // Update notification with the remaining time
                    updateNotification(countdownTime)

                    // Decrease countdown time
                    countdownTime--

                    // Schedule the next tick after 1 second
                    handler.postDelayed(this, 1000)
                } else {
                    stopSelf() // Stop the service when countdown reaches 0
                }
            }
        }
        handler.post(runnable) // Start the timer
    }

    private fun createNotification(time: Int): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My Foreground Service")
            .setContentText("Time remaining: $time seconds")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Ensure the notification can't be swiped away
    }

    private fun updateNotification(time: Int) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification(time).build()
        notificationManager.notify(NOTIFICATION_ID, notification) // Update the notification
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Stop the timer when service is destroyed
    }

    enum class MyForegroundServiceActions {
        START, STOP
    }
}
