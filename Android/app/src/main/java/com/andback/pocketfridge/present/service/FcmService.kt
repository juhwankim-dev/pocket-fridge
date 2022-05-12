package com.andback.pocketfridge.present.service

import android.annotation.SuppressLint
import android.provider.Settings
import com.andback.pocketfridge.data.model.FcmTokenEntity
import com.andback.pocketfridge.domain.usecase.user.SendFcmTokenUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class FcmService @Inject constructor(
    private val sendFcmTokenUseCase: SendFcmTokenUseCase
): FirebaseMessagingService() {

    @SuppressLint("HardwareIds")
    override fun onNewToken(token:String) {
        super.onNewToken(token)

        val androidId: String = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        // 서버에 업데이트
        sendFcmTokenUseCase(FcmTokenEntity(androidId, token)).subscribeOn(Schedulers.io())
            .subscribe()
    }


}