package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface IngreApi {
    @POST("foodingredient")
    fun uploadIngre(@Body ingredient: IngreEntityForUpload): Observable<BaseResponse<Any>>
}