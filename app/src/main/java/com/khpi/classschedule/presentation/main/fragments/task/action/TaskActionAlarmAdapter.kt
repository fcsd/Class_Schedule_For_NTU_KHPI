package com.khpi.classschedule.presentation.main.fragments.task.action

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.main.MainActivity

class TaskActionAlarmAdapter : BroadcastReceiver() {

    companion object {
        var NOTIFICATION_ID = "notification_id"
        var NOTIFICATION_TITLE = "notification_title"
        var NOTIFICATION_MESSAGE = "notification_message"
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(NOTIFICATION_TITLE)
        val message = intent.getStringExtra(NOTIFICATION_MESSAGE)

        val fragmentIntent = Intent(context, MainActivity::class.java)
                .putExtra(Constants.REQUEST_OPEN_TASK_INFO, id)

        val openIntent = PendingIntent.getActivity(context, 0,
                fragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, "Notification")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(false)
                .setContentText(message)
                .setContentIntent(openIntent)
                .setLights(ContextCompat.getColor(context, R.color.colorPrimary), 3000, 3000)

        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        builder.setVibrate(longArrayOf(0, 1000))
        notificationManager.notify(id, builder.build())
    }

}