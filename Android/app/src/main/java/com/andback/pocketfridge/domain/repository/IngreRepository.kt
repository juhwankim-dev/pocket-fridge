package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Single

interface IngreRepository {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>>
}