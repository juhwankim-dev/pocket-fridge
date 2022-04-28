package com.andback.pocketfridge.data.repository.category

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import io.reactivex.Observable

interface CategoryRemoteDataSource {
    fun getMainCategory(): Observable<BaseResponse<List<MainCategoryEntity>>>
    fun getSubCategory(): Observable<BaseResponse<List<SubCategoryEntity>>>
}