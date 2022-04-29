package com.andback.pocketfridge.data.repository.category

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import io.reactivex.Single

interface CategoryRemoteDataSource {
    fun getMainCategory(): Single<BaseResponse<List<MainCategoryEntity>>>
    fun getSubCategory(): Single<BaseResponse<List<SubCategoryEntity>>>
}