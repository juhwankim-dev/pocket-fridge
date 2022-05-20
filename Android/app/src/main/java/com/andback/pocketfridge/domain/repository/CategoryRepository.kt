package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import io.reactivex.Single

interface CategoryRepository {
    fun getMainCategory(): Single<BaseResponse<List<MainCategoryEntity>>>
    fun getSubCategory(): Single<BaseResponse<List<SubCategoryEntity>>>
}