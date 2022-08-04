package com.tonotes.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.tonotes.core.util.Constants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val backupStatusChannel = NotificationChannel(
                Constants.NotificationChannel.BACKUP_STATUS_ID,
                Constants.NotificationChannel.BACKUP_STATUS_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = Constants.NotificationChannel.BACKUP_STATUS_DESCRIPTION
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(backupStatusChannel)
        }
    }
}