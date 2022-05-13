package com.andback.pocketfridge.domain.usecase.notification

import com.andback.pocketfridge.domain.infra.IngreExpiryNotiManager
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.GetIngreListForNotiUseCase
import com.andback.pocketfridge.present.config.EXPIRY_DATE_PREFERENCE
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendIngreExpiryNotiUseCase @Inject constructor(
    private val readDataStoreUseCase: ReadDataStoreUseCase,
    private val ingreExpiryNotiManager: IngreExpiryNotiManager,
    private val getIngreListForNotiUseCase: GetIngreListForNotiUseCase
) {
    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val date = readDataStoreUseCase.execute(EXPIRY_DATE_PREFERENCE)

            if(date != null) {
                getIngreListForNotiUseCase(date.toInt()).subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            sendNoti(date.toInt(), it)
                        },
                        {
                            
                        }
                    )
            }
        }
    }

    private fun sendNoti(date: Int, list: List<Ingredient>) {
        val title = if(date == 0) {
            "D-day"
        } else {
            "D-${date}"
        }

        if(list.isNotEmpty()) {
            val body = if(list.size > 1) {
                """
                    ${list[0].name} 외 ${list.size - 1}개의 품목의 유통기한이 임박했습니다.
                """.trimIndent()
            } else {
                "${list[0].name}의 유통기한이 임박했습니다."
            }
            ingreExpiryNotiManager.sendNoti(title, body)
        }
    }
}