package com.andback.pocketfridge.data.repository.category

import com.andback.pocketfridge.data.api.CategoryApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import io.reactivex.Single
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val categoryApi: CategoryApi
): CategoryRemoteDataSource {
    override fun getMainCategory(): Single<BaseResponse<List<MainCategoryEntity>>> = categoryApi.getMainCategory()
    override fun getSubCategory(): Single<BaseResponse<List<SubCategoryEntity>>> = categoryApi.getSubCategory()
}