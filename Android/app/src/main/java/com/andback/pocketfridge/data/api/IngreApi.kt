package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface IngreApi {
    @POST("foodIngredient/insert")
    fun uploadIngre(@Body ingredient: IngreEntityForUpload): Single<TempEntity>
}