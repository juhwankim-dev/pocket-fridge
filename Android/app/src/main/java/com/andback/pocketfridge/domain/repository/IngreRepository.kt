package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Observable

interface IngreRepository {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Observable<BaseResponse<Any>>
}