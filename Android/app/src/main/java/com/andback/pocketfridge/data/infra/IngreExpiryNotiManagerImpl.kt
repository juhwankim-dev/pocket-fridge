package com.andback.pocketfridge.data.infra

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.andback.pocketfridge.R
import com.andback.pocketfridge.domain.infra.IngreExpiryNotiManager
import com.andback.pocketfridge.present.config.INGRE_EXPIRY_NOTI_ID
import com.andback.pocketfridge.present.views.main.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class IngreExpiryNotiManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
): IngreExpiryNotiManager {
    private val systemNotiManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notifyIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val notifyPendingIntent = PendingIntent.getActivity(
        context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    override fun sendNoti(title: String, body: String) {
        val notiBuilder = NotificationCompat.Builder(context, INGRE_EXPIRY_NOTI_ID)

        val noti = notiBuilder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notifyPendingIntent)
            .build()

        systemNotiManager.notify(1, noti)
    }
}