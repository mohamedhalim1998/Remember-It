package com.mohamed.halim.essa.flashcards.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mohamed.halim.essa.flashcards.MainActivity
import com.mohamed.halim.essa.flashcards.R


val NOTI_CH_ID: String = "Practice Notification"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            NOTI_CH_ID
            , "Practice Notification Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val attributes =
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT).build()
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        channel.setSound(sound, attributes)
        channel.enableVibration(true)
        val manager: NotificationManager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

fun notify(context: Context) {
    val notificationManager =
        NotificationManagerCompat.from(context)
    val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTI_CH_ID)
    builder.setSmallIcon(R.drawable.ic_action_done)
    builder.setContentTitle(context.getString(R.string.notification_title))
    builder.setContentText(context.getString(R.string.notification_content))
    val sound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    builder.setSound(sound)
    builder.setDefaults(Notification.DEFAULT_ALL)
    builder.setPriority(NotificationCompat.PRIORITY_HIGH)
    builder.setAutoCancel(true)

    val i = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, i,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    builder.setContentIntent(pendingIntent)
    notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
}