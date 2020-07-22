package com.mohamed.halim.essa.flashcards.preferencescreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mohamed.halim.essa.flashcards.util.notify
import java.util.*


private const val PRACTICE_REMINDER_ID = 251;

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        notify(context!!)
    }
}

fun setAlarm(
    context: Context,
    timeCalender: Calendar
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)

    val pendingIntent = PendingIntent.getBroadcast(context, PRACTICE_REMINDER_ID, intent, 0)
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        timeCalender.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

}

fun dismissAlarm(context: Context) {
    val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, PRACTICE_REMINDER_ID, intent, 0)
    alarmManager.cancel(pendingIntent)
}