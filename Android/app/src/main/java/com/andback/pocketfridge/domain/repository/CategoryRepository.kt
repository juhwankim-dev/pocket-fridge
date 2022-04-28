package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import io.reactivex.Observable

interface CategoryRepository {
    fun getMainCategory(): Observable<BaseResponse<List<MainCategoryEntity>>>
    fun getSubCategory(): Observable<BaseResponse<List<SubCategoryEntity>>>
}