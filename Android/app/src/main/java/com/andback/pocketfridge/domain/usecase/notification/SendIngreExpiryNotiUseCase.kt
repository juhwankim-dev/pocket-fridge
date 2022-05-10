package com.andback.pocketfridge.domain.usecase.notification

import com.andback.pocketfridge.domain.infra.IngreExpiryNotiManager
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.present.config.EXPIRY_DATE_PREFERENCE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendIngreExpiryNotiUseCase @Inject constructor(
    private val readDataStoreUseCase: ReadDataStoreUseCase,
    private val ingreExpiryNotiManager: IngreExpiryNotiManager
) {
    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val date = readDataStoreUseCase.execute(EXPIRY_DATE_PREFERENCE)

            if(date != null) {
                val title = "D-${date.toInt()}"
                // TODO: body IngreRepository를 통해 값 가져오기
                val body = ""
                ingreExpiryNotiManager.sendNoti(title, body)
            }

        }
    }
}