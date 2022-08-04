package com.tonotes.note_data.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.tonotes.core.util.Constants.NotificationChannel
import com.tonotes.core_ui.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BackupStatusNotificationService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: String) {
        val notification = NotificationCompat.Builder(context, NotificationChannel.BACKUP_STATUS_ID)
            .setSmallIcon(R.drawable.ic_baseline_description_24)
            .setContentTitle(context.getString(R.string.backup_status))
            .setContentText(message)
            .build()

        notificationManager.notify(1, notification)
    }
}