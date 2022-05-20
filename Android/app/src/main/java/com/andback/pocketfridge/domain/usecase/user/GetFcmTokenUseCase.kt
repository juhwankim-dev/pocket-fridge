package com.andback.pocketfridge.domain.usecase.user

import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Single
import javax.inject.Inject

/**
 * FCM 토큰은 로그인 할 때마다 가져와서 서버에 보내야한다.
 */
class GetFcmTokenUseCase @Inject constructor() {
    operator fun invoke(): Single<String> {
        return Single.create { emitter ->
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token -> emitter.onSuccess(token) }
                .addOnFailureListener { e -> emitter.onError(e)}
        }
    }
}