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
import com.khpi.classschedule.App
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.presentation.main.MainActivity
import javax.inject.Inject

class TaskActionAlarmAdapter : BroadcastReceiver() {

    //@formatter:off
    @Inject lateinit var settingsRepository: SettingsRepository
    //@formatter:on

    init {
         App.dagger.inject(this)
    }

    companion object {
        val NOTIFICATION_ID = "notification_id"
        val NOTIFICATION_TITLE = "notification_title"
        val NOTIFICATION_MESSAGE = "notification_message"
        val NOTIFICATION_CHANNEL = "notification_channel"
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(NOTIFICATION_TITLE)
        val message = intent.getStringExtra(NOTIFICATION_MESSAGE)
        val channel = intent.getStringExtra(NOTIFICATION_CHANNEL)

        val fragmentIntent = Intent(context, MainActivity::class.java)
                .putExtra(Constants.REQUEST_OPEN_TASK_INFO, id)

        val openIntent = PendingIntent.getActivity(context, 0,
                fragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val vibrate = settingsRepository.getPreferenceByKey(Constants.VIBRATE)
        val sound = settingsRepository.getPreferenceByKey(Constants.SOUND)

        val builder = NotificationCompat.Builder(context, channel)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(false)
                .setContentText(message)
                .setContentIntent(openIntent)
                .setLights(ContextCompat.getColor(context, R.color.colorPrimary), 3000, 3000)

        if (sound) {
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        }

        if (vibrate) {
            builder.setVibrate(longArrayOf(0, 1000))
        }

        notificationManager.notify(id, builder.build())
    }

}