package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.domain.model.Ingredient
import io.reactivex.Single

interface IngreRepository {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>>
    fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<Ingredient>>>
    fun deleteIngreById(ingreId: Int): Single<BaseResponse<Any>>
    fun updateIngre(ingredient: Ingredient): Single<BaseResponse<Any>>
}