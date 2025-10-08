package com.example.fitnesstracker.data.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.fitnesstracker.R
import com.example.fitnesstracker.utils.TrackingUtil.ACTION_PAUSE_SERVICE
import com.example.fitnesstracker.utils.TrackingUtil.ACTION_SHOW_TRACKING_ACTIVITY
import com.example.fitnesstracker.utils.TrackingUtil.ACTION_START_OR_RESUME_SERVICE
import com.example.fitnesstracker.utils.TrackingUtil.ACTION_STOP_SERVICE
import com.example.fitnesstracker.utils.TrackingUtil.NOTIFICATION_CHANNEL_ID
import com.example.fitnesstracker.utils.TrackingUtil.NOTIFICATION_CHANNEL_NAME
import com.example.fitnesstracker.utils.TrackingUtil.NOTIFICATION_ID
import com.example.fitnesstracker.view.activities.RecordActivity

class TrackingService : LifecycleService() {
    var isFirstRun = true
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {

                    }
                }

                ACTION_PAUSE_SERVICE -> {

                }

                ACTION_STOP_SERVICE -> {
                    stopService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopService() {
        isFirstRun = true
        stopForeground(true)
        stopSelf()
    }

    private fun startForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_fit)
            .setContentTitle("Fitness Tracker")
            .setContentText("00:00:00")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, RecordActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_ACTIVITY
        },
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}