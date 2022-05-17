package com.andback.pocketfridge.present.service

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.andback.pocketfridge.data.model.FcmTokenEntity
import com.andback.pocketfridge.domain.usecase.user.SendFcmTokenUseCase
import com.andback.pocketfridge.present.views.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class FcmService: FirebaseMessagingService() {
    @Inject
    lateinit var sendFcmTokenUseCase: SendFcmTokenUseCase

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: ${message.data}")
        Log.d(TAG, "onMessageReceived: ${message.notification?.title}")
        Log.d(TAG, "onMessageReceived: ${message.notification?.body}")

        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply { action = MainActivity.INTENT_ACTION })
    }

    companion object {
        private const val TAG = "FcmService_debuk"
    }
}