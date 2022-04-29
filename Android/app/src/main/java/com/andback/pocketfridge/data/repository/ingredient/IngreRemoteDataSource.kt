package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Observable
import io.reactivex.Single

interface IngreRemoteDataSource {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Observable<BaseResponse<Any>>
    fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<IngreEntity>>>
}