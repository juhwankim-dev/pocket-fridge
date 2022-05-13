package com.andback.pocketfridge.data.infra

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.andback.pocketfridge.R
import com.andback.pocketfridge.domain.infra.IngreExpiryNotiManager
import com.andback.pocketfridge.present.config.INGRE_EXPIRY_NOTI_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class IngreExpiryNotiManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
): IngreExpiryNotiManager {
    private val systemNotiManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun sendNoti(title: String, body: String) {
        val notiBuilder = NotificationCompat.Builder(context, INGRE_EXPIRY_NOTI_ID)
        val noti = notiBuilder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        systemNotiManager.notify(1, noti)
    }
}