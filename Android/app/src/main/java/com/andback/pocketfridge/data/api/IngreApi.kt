package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Single
import retrofit2.http.*

interface IngreApi {
    @POST("foodingredient")
    fun uploadIngre(@Body ingredient: IngreEntityForUpload): Single<BaseResponse<Any>>

    @GET("foodingredient/{refrigeratorId}")
    fun getIngreListByFridgeId(@Path("refrigeratorId") fridgeId: Int): Single<BaseResponse<List<IngreEntity>>>

    @GET("/foodingredient")
    fun getAllIngreList(): Single<BaseResponse<List<IngreEntity>>>

    @DELETE("foodingredient/{ingreId}")
    fun deleteIngreById(@Path("ingreId") ingreId: Int): Single<BaseResponse<Any>>

    @PUT("foodingredient")
    fun updateIngre(@Body ingre: IngreEntity): Single<BaseResponse<Any>>
}