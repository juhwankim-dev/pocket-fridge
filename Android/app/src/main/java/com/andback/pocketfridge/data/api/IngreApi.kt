package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IngreApi {
    @POST("foodingredient")
    fun uploadIngre(@Body ingredient: IngreEntityForUpload): Observable<BaseResponse<Any>>

    @GET("foodingredient/{refrigeratorId}")
    fun getIngreListByFridgeId(@Path("refrigeratorId") fridgeId: Int): Single<BaseResponse<List<IngreEntity>>>
}