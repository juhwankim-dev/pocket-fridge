package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface CategoryApi {
    @GET("foodingredient/maincategory")
    fun getMainCategory(): Observable<BaseResponse<List<MainCategoryEntity>>>

    @GET("foodingredient/subcategory")
    fun getSubCategory(): Observable<BaseResponse<List<SubCategoryEntity>>>
}