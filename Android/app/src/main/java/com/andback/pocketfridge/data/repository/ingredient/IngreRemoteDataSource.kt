package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Path

interface IngreRemoteDataSource {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>>
    fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<IngreEntity>>>
    fun deleteIngreById(ingreId: Int): Single<BaseResponse<Any>>
    fun updateIngre(ingreId: Int, ingre: IngreEntity): Single<BaseResponse<Any>>
}