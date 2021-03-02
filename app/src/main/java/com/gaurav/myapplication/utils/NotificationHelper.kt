package com.gaurav.myapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.gaurav.myapplication.R
import com.gaurav.myapplication.ui.activity.MainActivity

class NotificationHelper(private val mContext: Context, private val appPrefs: AppPreferences) {

    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mBuilder: NotificationCompat.Builder

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "10001"
    }

    /**
     * Create and push the notification
     */
    fun showNotification(title: String?, message: String) {
        val resultIntent = Intent(mContext, MainActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appPrefs.notificationMessageBody = message
        val resultPendingIntent = PendingIntent.getActivity(
                mContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )

        mBuilder = NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
        mBuilder.apply {
            setSmallIcon(R.drawable.ic_baseline_message_24).color = ContextCompat.getColor(mContext, R.color.purple_700)
            setContentTitle(title?.orEmpty())
            setContentText(message).color = ContextCompat.getColor(mContext, R.color.purple_700)
            setAutoCancel(true)
            setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            setContentIntent(resultPendingIntent)
        }

        mNotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "New Sms", importance)
            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build())
    }
}
